from datetime import datetime


def timestamp():
    return datetime.now().strftime('%Y/%m/%d %H:%M:%S')


def today():
    return datetime.now().strftime('%Y%m%d')


