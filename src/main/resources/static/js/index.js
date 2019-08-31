const INDEX_APP = (() => {
    'use strict';

    const IndexController = function () {
        const indexService = new IndexService();
        //const observer = OBSERVER_APP.observeService();

        const init = () => {
            indexService.loadArticles();
        };

        return {
            init: init,
        };

    };

    const IndexService = function () {
        const connector = FETCH_APP.FetchApi();
        const fileLoader = FILE_LOAD_APP.FileLoadService();
        const template = TEMPLATE_APP.TemplateService();
        const headerService = HEADER_APP.HeaderService();

        const cards = document.getElementById('cards');

        // TODO search-result.js와 중복!!
        const loadArticles = () => {
            const handleArticleInfo = articleInfo => {
                cards.insertAdjacentHTML('beforeend', template.card(articleInfo));
                fileLoader.loadMediaFile(fileLoader, articleInfo.articleFileName, articleInfo.articleId);
                fileLoader.loadProfileImageFile(fileLoader, articleInfo.userId, "thumb-img-user-");
            };

            const handleResponse = articleInfos => {
                articleInfos.forEach(handleArticleInfo);
                headerService.applyHashTag();
            };

            const addArticle = response => {
                response.json()
                    .then(handleResponse);
            };
            connector.fetchTemplateWithoutBody(`/api/articles`, connector.GET, addArticle);
        };

        return {
            loadArticles: loadArticles,
        };
    };

    const init = () => {
        const indexController = new IndexController();
        indexController.init();
    };

    return {
        init: init,
    }

})();

INDEX_APP.init();