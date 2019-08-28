const COMMENT_APP = (() => {

    const CommentController = function() {
        const commentService = new CommentService();

        const saveComment = () => {
            const commentSaveButtons = document.getElementsByClassName("comment-save-button");

            for (let i = 0; i < commentSaveButtons.length; i++) {
                commentSaveButtons.item(i).addEventListener("click", commentService.save);
            }
        };

        const showComment = () => {
            const showCommentButtons = document.getElementsByClassName("show-comment");
            for (let i = 0; i < showCommentButtons.length; i++) {
                showCommentButtons.item(i).addEventListener("click", commentService.show);
            }
        };


        const deleteComment = () => {

        };

        const init = () => {
            saveComment();
            showComment();
        };

        return {
            init: init,
        };

    };

    const CommentService = function() {
        const save = event => {
            const connectors = FETCH_APP.FetchApi();
            let target = event.target;
            let targetArticleId = target.parentNode.parentNode.getAttribute("data-article-id");
            let commentContents = target.parentNode.parentNode.querySelector("textarea").value;

            const header = {
                'Content-Type': 'application/json; charset=UTF-8',
                'Accept': 'application/json'
            };

            const commentInfo = {
                articleId: targetArticleId,
                contents: commentContents,
            };

            connectors.fetchTemplate(`/api/comments`,
                connectors.POST,
                header,
                JSON.stringify(commentInfo),
                () => alert('댓글 작성 성공'));
        };

        const show = event => {
            const connectors = FETCH_APP.FetchApi();
            const commentTemplate =
                `
               {{#comments}}
               <li class="comment-item no-pdd">
                   <div class="info pdd-left-15 pdd-vertical-5">
                       <a href="" class="title no-pdd-vertical text-bold inline-block font-size-15">{{user.nickName}}</a>
                       <span class="font-size-14">{{contents}}</span>
                       <span></span>
                       <time class="font-size-8 text-gray d-block">{{writeTime}}</time>
                   </div>
               </li>
               {{/comments}}`;

            let commentItemTemplate = Handlebars.compile(commentTemplate);
            let target = event.target;
            let targetArticleId = target.parentNode.querySelector('.add-comment').getAttribute('data-article-id');
            let commentList = target.parentNode.querySelector('ul');

            connectors.fetchTemplateWithoutBody(`/api/comments/${targetArticleId}`,
                connectors.GET,
                (response) => {
                    response.json().then(res => {
                        let data = {comments: res};
                        commentList.insertAdjacentHTML('afterbegin', commentItemTemplate(data));
                    });
                }
            )
        };

        return {
            save: save,
            show: show,
        }

    };

    const init = () => {
        const commentController = new CommentController();
        commentController.init()
    };

    return {
        init: init,
    };

})();


