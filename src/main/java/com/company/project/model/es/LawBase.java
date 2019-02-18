package com.company.project.model.es;

public class LawBase {

        public LawBase(String title, String url) {
            this.title = title;
            this.url = url;
        }

        /**
         * title : 《中华人民共和国广告法》
         * url : http://law.wkinfo.com.cn//search/process?collection=adminPenalty&sp_term=%E3%80%8A%E4%B8%AD%E5%8D%8E%E4%BA%BA%E6%B0%91%E5%85%B1%E5%92%8C%E5%9B%BD%E5%B9%BF%E5%91%8A%E6%B3%95%E3%80%8B&termField=lawAccording&accuracy=accuracy
         */

        private String title;
        private String url;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
