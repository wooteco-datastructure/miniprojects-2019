const SEARCH_APP = (() => {

    const SearchController = function () {
        const searchService = new SearchService();

        const loadArticleByObserve = () => {
            let page = 0;
            const io = new IntersectionObserver(entries => {
                entries.forEach(entry => {
                    if (!entry.isIntersecting) {
                        return;
                    }

                    searchService.loadArticles(page++);
                });

            });

            io.observe(document.getElementById('end'));
        };

        const init = () => {
            loadArticleByObserve();
        };

        return {
            init: init
        }
    };

    const SearchService = function () {
        const headerService = HEADER_APP.HeaderService();
        const connector = FETCH_APP.FetchApi();
        const fileLoader = FILE_LOAD_APP.FileLoadService();

        const query = document.getElementById('query').innerText;
        const count = document.getElementById('count');
        const cards = document.getElementById('cards');

        const loadArticles = page => {
            const addArticle = response => {
                response.json()
                    .then(data => {
                        count.innerText = data.totalElements;

                        data.content.forEach(hashTag => {
                            const article = hashTag.article;

                            // TODO template 좀 다른 곳으로 빼고 싶음ㅠ_ㅠ
                            const cardTemplate =
                                `<div class="card widget-feed no-pdd mrg-btm-70 shadow-sm">
                                    <div class="feed-header padding-15">
                                        <ul class="list-unstyled list-info">
                                            <li>
                                                <img class="thumb-img img-circle" src="/images/default/default_profile.png"
                                                     alt="">
                                                <div class="info">
                                                    <a href="" class="title no-pdd-vertical text-bold inline-block font-size-15">
                                                        ${article.author.nickName}</a>
                                                </div>
                                            </li>
                                            <a class="pointer absolute top-10 right-0" data-toggle="dropdown"
                                               aria-expanded="false">
                                        <span class="btn-icon text-dark">
                                            <i class="ti-more font-size-16"></i>
                                        </span>
                                            </a>
                                            <ul class="dropdown-menu">
                                                <li>
                                                    <a id="article-edit-${article.id}" data-id="${article.id}" class="pointer article-edit">
                                                        <i class="ti-pencil pdd-right-10 text-dark"></i>
                                                        <span class="">게시글 수정</span>
                                                    </a>
                                                    <a id="article-delete-${article.id}" data-id="${article.id}" class="pointer article-delete">
                                                        <i class="ti-trash pdd-right-10 text-dark"></i>
                                                        <span class="">게시글 삭제</span>
                                                    </a>
                                                </li>
                                            </ul>
                                        </ul>
                                    </div>
                                    <div class="feed-body no-pdd">
                                        <img id="article-image-${article.id}" class="img-fluid" src="" alt=""
                                             style="display: none; width: 100%;">
                                        <video id="article-video-${article.id}" controls autoplay loop src=""
                                               style="display: none; width: 100%;">
                                        </video>
                                    </div>
                                    <ul class="feed-action pdd-horizon-15 pdd-top-5">
                                        <li>
                                            <a href="">
                                                <i class="fa fa-heart activated-heart font-size-25"></i>
                                            </a>
                                        </li>
                                        <li>
                                            <a href="">
                                                <i class="ti-comment font-size-22"></i>
                                            </a>
                                        </li>
                                        <li>
                                            <i class="ti-export font-size-22"
                                               onclick="copyUrl(${article.id})"></i>
                                        </li>

                                        <li class="float-right">
                                            <a href="" class="pdd-right-0">
                                                <i class="fa fa-bookmark font-size-25"></i>
                                            </a>
                                        </li>
                                    </ul>
                                    <div class="feedback-status-container pdd-horizon-15">
                                        <img class="mini-profile-img" src="/images/default/eastjun_profile.jpg">
                                        <p class="no-mrg pdd-left-5 d-inline-block">
                                            <span class="text-bold">eastjun</span>님 외 <span class="text-bold">37</span>명이
                                            좋아합니다.
                                        </p>
                                    </div>
                                    <div id="'article-contents-${article.id}" class="feed-contents pdd-left-15">
                                        <p class="contents">${article.contents}</p>
                                        <form style="display: none;">
                                            <input type="text" value="${article.contents}">
                                            <button type="button">수정</button>
                                        </form>
                                    </div>
                                    <div class="feed-footer">
                                        <div class="comment">
                                            <ul class="list-unstyled list-info pdd-horizon-5">
                                                <li class="comment-item no-pdd">
                                                    <div class="info pdd-left-15 pdd-vertical-5">
                                                        <a href=""
                                                           class="title no-pdd-vertical text-bold inline-block font-size-15">brown2</a>
                                                        <span class="font-size-14">안돌 어디갔다 온거였지?</span>
                                                        <time class="font-size-8 text-gray d-block">12시간 전</time>
                                                    </div>
                                                </li>
                                            </ul>
                                            <div class="add-comment relative">
                                        <textarea rows="1" class="form-control text-dark padding-15"
                                                  placeholder="댓글 달기..."></textarea>
                                                <div class="absolute top-5 right-0">
                                                    <button class="btn btn-default no-border text-gray">게시</button>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>`;

                            fileLoader.loadMediaFile(fileLoader, `${article.fileInfo.fileName}`, `${article.id}`);

                            cards.insertAdjacentHTML('beforeend', cardTemplate);

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
