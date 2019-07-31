# NaverFest
For Naver Mini Festival 
## 제출자
한양대학교 중어중문학과 한가희 
## 앱 이름
THE Slider 
## 시연 영상 주소
https://drive.google.com/open?id=11snoElt0UCyRB0rlNJZ6VzG7t3L95dxt
## 앱 설명
처음엔 다양한 기능을 가진 슬라이더를 만들면 어떨까 했습니다만,  라이브 슬라이더라는 것을 처음 구현해 보는 입장이다 보니 제한된 기간 안에 만들기가 부담스럽다는 것을 깨달았습니다. 이로 인해 네이버에서 구현하도록 한 요구 사항에 최대한 충실하고자 3 개의 다른 사이트에서 읽어온 rss 를 각기 다른 특성의 슬라이더 안에 띄워주고,  검색 기능을 가진 단순한 뉴스 앱 슬라이더를 만들었습니다. 
## 라이선스 
MIT License
## 구독한 RSS 주소 
Reuters Top News Videos  http://feeds.reuters.com/reuters/USVideoTopNews
World Wildlife Fund http://feeds.feedburner.com/WWFStories
TIME Entertainment http://feeds2.feedburner.com/time/entertainment
## 앱의 전반적인 특징
안드로이드 개발은 Java, 이미지 레이블링을 처리하는 서버는 Python을 사용하였습니다. 전반적인 아키텍처는 MVVM 아키텍처 방식을 따르고자 노력하였습니다. 

## [View Model 을 활용한 UI]
### THE Slider 는 앱 전반에 걸쳐서 ViewModel 과 LiveData를 적극적으로 활용하였습니다. 
1.	슬라이더 하단의 프로그레스 바의 이동 상태를 ViewModel이 Observe 하여서 해당 데이터를 구독하고 있는 다른 뷰 홀더에 정보를 알려줌으로써 슬라이더와 프로그래스 바가 따로 움직이지 않도록 하였습니다. 

2.	Retrofit2 의 SimpleXmlConverterFactory를 활용해 Rss 주소로부터 정보를 읽어오는 데, 이 때 정보를 읽어오는 것 과 동시에 MutableLiveData에 정보를 저장함으로써 Rss 에서 읽어 온 정보가 변화하였을 경우 바로 UI가 업데이트 되도록 하였습니다. 

3.	하단의 ViewPager 가 이동함에 따라서 상단에는 하단 ViewPager 로 부터 받아온 정보를 띄우는 UI를 구상하였습니다. 이 때 ViewModel 을 통해 하단의 뷰 페이저가 초기화 될 때 마다 상단의 뷰 페이저에는 해당 기사 안의 이미지와 비디오 썸네일이 바로 뜰 수 있도록 하였습니다. 
## [Google Vision API 및 서버]
### Google Vision API 를 활용해 Image Labeling 을 하여 검색 결과에 반영되도록 하였습니다. 
1.	클라이언트 쪽에서 POST를 하는 부분은 Retrofit2 대신 HttpUrlConnection 과 AsyncTask를 통해 직접 String으로 받아오도록 하였습니다. 응답의 key 값이 dynamic한 이미지 Url로 설정이 되어 응답이 돌아오기 때문에 Java 모델 클래스를 만드는 데 어려움이 있었기 때문입니다. 
a.	AsyncTask 를 선택한 이유는 화면을portrait 모드로 고정시켜 놓았고, 이로 인해 흔히 AsyncTask에서 발생하는 메모리 누수가 큰 문제가 되지 않았기 때문입니다. 더불어 외부 클래스에서 액티비티이든 일반 Util파일이든 유연하게 AsyncTask.execute()를 호출할 수 있다는 장점도 있었습니다.
2.	score 값이 0.8 이상인 텍스트들만 검색 결과에 반영하였습니다. 
