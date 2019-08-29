const MY_FEED_APP = (() => {
    'use strict';

    const MyFeedController = function () {
        const myFeedService = new MyFeedService();
        const observer = OBSERVER_APP.observeService();

        const init = () => {
            observer.loadByObserve(myFeedService.loadArticles);
        };

        return {
            init: init,
        };

    };

    const MyFeedService = function () {
        const connector = FETCH_APP.FetchApi();
        const fileLoader = FILE_LOAD_APP.FileLoadService();
        const template = TEMPLATE_APP.TemplateService();
        const headerService = HEADER_APP.HeaderService();
        const cards = document.getElementById('cards');
        const articleCount = document.getElementById('article-count');


        // TODO search-result.js와 중복!!
        const loadArticles = page => {

            const addArticle = response => {
                response.json()
                    .then(data => {
                        debugger;
                        articleCount.innerText = data.totalElements;

                        data.content.forEach(articleInfo => {
                            fileLoader.loadMediaFile(fileLoader, `${articleInfo.articleFileName}`, `${articleInfo.articleId}`);
                            fileLoader.loadProfileImageFile(fileLoader, `${articleInfo.userId}`, "thumb-img-user-");
                            cards.insertAdjacentHTML('beforeend', template.card(articleInfo));
                        });
                        headerService.applyHashTag();
                    });
            };

            connector.fetchTemplateWithoutBody(`/api/articles/users/${userNickname}?page=${page}`, connector.GET, addArticle);
        };

        return {
            loadArticles: loadArticles
        };
    };

    const init = () => {
        const myFeedController = new MyFeedController();
        myFeedController.init();
    };

    return {
        init: init,
    }

})();

MY_FEED_APP.init();