# Flask API Server for mini D2 Fest.

### Purpose
Many of images on the RSS can be also searchable item not only texts.<br/>
So I planed to make a "scalable search function", based on image label-annotation with Deep Learning.<br/>
But there are some issues in making Deep Learning Model be on-device. Furthermore, it is beyond the scope of mini D2 Fest.<br/>
So I decided to use Google Vision API in my server system.<br/>
I will replace this Google Vision API later when I build my own Deep Learning Model.<br/>

### How to run your server.
#### 1. Install google cloud sdk.
#### 2. Register GOOGLE_APPLICATION_CREDENTIALS path in your bashrc.
#### 3. Make config.json file in the same directory.
```
{
  "DEFAULT": {
    "THREAD_NUM": 4,
    "BASE_DIR": "/home/user/flack_api_server/",
    "SERVER_IP": "0.0.0.0",
    "SERVER_PORT": "8000",
    "LOGGING_PATH": ""
  }
}
```
#### 4. Install requirements.
```
pip install -r requirements.txt
```
#### 5. Run.
```
python app.py
```