    package com.example.medifiles;

    public class VideoItem {
        private String videoName;
        private String videoLength;
        private String videoUrl; // 비디오 URL을 저장할 변수 추가

        public VideoItem(String videoName, String videoLength, String videoUrl) {
            this.videoName = videoName;
            this.videoLength = videoLength;
            this.videoUrl = videoUrl;
        }

        public String getVideoName() {
            return videoName;
        }

        public String getVideoLength() {
            return videoLength;
        }

        public String getVideoUrl() {
            return videoUrl;
        }
    }
