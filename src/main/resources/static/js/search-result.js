const SEARCH_APP = (() => {

    const SearchController = function () {
        const searchService = new SearchService();
        const loadArticles = searchService.loadArticles;
        const observer = OBSERVER_APP.observeService();

        const init = () => {
            observer.loadByObserve(loadArticles);
        };

        return {
            init: init
        }
    };

    const SearchService = function () {
        const headerService = HEADER_APP.HeaderService();
        const connector = FETCH_APP.FetchApi();
        const fileLoader = FILE_LOAD_APP.FileLoadService();
        const template = TEMPLATE_APP.TemplateService();

        const queryInput = document.getElementById('query');
        const count = document.getElementById('count');
        const cards = document.getElementById('cards');

        const loadArticles = page => {
            const query = queryInput.innerText;
            const addArticle = response => {
                response.json()
                    .then(data => {
                        count.innerText = data.totalElements;

                        data.content.forEach(hashTag => {
                            const article = hashTag.article;
                            fileLoader.loadMediaFile(fileLoader, `${article.fileInfo.fileName}`, `${article.id}`);
                            fileLoader.loadProfileImageFile(fileLoader, `${article.id}`, `${article.author.id}`);
                            cards.insertAdjacentHTML('beforeend', template.card(article));
                        });
                        headerService.applyHashTag();
                    });
            };

            connector.fetchTemplateWithoutBody(`/api/hashTag/${query}?page=${page}`, connector.GET, addArticle);
        };

        return {
            loadArticles: loadArticles,
        }
    };

    const init = () => {
        const searchController = new SearchController();
        searchController.init();
    };

    return {
        init: init
    }
})();

SEARCH_APP.init();
