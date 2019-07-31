from flask import Flask
from flask_restful import Resource, Api, reqparse
from google.cloud import vision
import logging
from conf import *

app = Flask(__name__)
api = Api(app)
client = vision.ImageAnnotatorClient()

Logger = None

def setLogger():
    logger = logging.getLogger("server_log")
    logger.setLevel(logging.INFO)
    formatter = logging.Formatter('[%(levelname)s|%(asctime)s] -> %(message)s')
    fileHandler = logging.FileHandler(log_path)
    fileHandler.setFormatter(formatter)
    logger.addHandler(fileHandler)
    return logger


class GetImageLabel(Resource):

    def get_label(self, urls):
        result = {}
        for url in urls:
            Logger.info(url)
            response = client.annotate_image({
                'image': {
                    'source': {'image_uri': url},
                },
                'features': [{'type': vision.enums.Feature.Type.LABEL_DETECTION}], })
            url_res = []
            for res in response.label_annotations:
                temp = dict()
                temp['description'] = res.description
                temp['score'] = res.score
                url_res.append(temp)
            result[url] = url_res

        return result

    def post(self):
        try:
            parser = reqparse.RequestParser()
            parser.add_argument('urls', type=str, action='append')
            args = parser.parse_args()
            labels = self.get_label(args['urls'])

            return {'results': labels}

        except Exception as e:
            Logger.error(str(e))
            return {'error': str(e)}


@app.route('/')
def hello_world():
    return 'I GaHee'


api.add_resource(GetImageLabel, '/imageLabel')
if __name__ == '__main__':
    Logger = setLogger()
    logging.info("SERVER START")
    app.run(host=ip, port=port, debug=False)
    logging.info("SERVER END")
