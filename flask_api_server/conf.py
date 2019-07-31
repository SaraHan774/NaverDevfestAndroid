import json

with open('config.json', 'r') as f:
    config = json.load(f)


n_thread = config["DEFAULT"]["THREAD_NUM"]
base_dir = config["DEFAULT"]["BASE_DIR"]
ip = config["DEFAULT"]["SERVER_IP"]
port = config["DEFAULT"]["SERVER_PORT"]
log_path = config["DEFAULT"]["LOGGING_PATH"]
