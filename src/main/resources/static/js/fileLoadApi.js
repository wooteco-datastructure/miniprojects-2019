const FILE_LOAD_APP = (() => {

    const FileLoadService = function () {
        const b64StringToBlob = b64Data => {
            const byteCharacters = atob(b64Data);
            const byteArrays = [];
            const sliceSize = 512;

            for (let offset = 0; offset < byteCharacters.length; offset += sliceSize) {
                const slice = byteCharacters.slice(offset, offset + sliceSize);
                const byteNumbers = new Array(slice.length);
                for (let i = 0; i < slice.length; i++) {
                    byteNumbers[i] = slice.charCodeAt(i);
                }
                const byteArray = new Uint8Array(byteNumbers);
                byteArrays.push(byteArray);
            }
            return new Blob(byteArrays, {type: ''});
        };

        const setSrcAttribute = (url, fileName, articleId) => {
            const words = fileName.split('.');
            const extension = words[words.length - 1];

            const imageExtensions = ['jpg', 'png', 'jpeg'];
            const videoExtensions = ['avi', 'mp4'];

            if (imageExtensions.includes(extension)) {
                const articleImage = document.getElementById('article-image-' + articleId);
                articleImage.style.display = "block";
                articleImage.src = url;
            }
            if (videoExtensions.includes(extension)) {
                const articleVideo = document.getElementById('article-video-' + articleId);
                articleVideo.style.display = "block";
                articleVideo.src = url;
            }
        };


        //todo setSrcAttribute와 중복!!
        const setProfileSrcAttribute = (url, className) => {
            const articleImages = document.getElementsByClassName(className);

            for (i = 0; i < articleImages.length; i++) {
                articleImages[i].style.display = "block";
                articleImages[i].src = url;
            }
        };

        const loadMediaFile = (fileLoader, fileName, id) => {
            const connector  = FETCH_APP.FetchApi();

            const loadFile = response => {
                response.arrayBuffer().then(buffer => {
                    const bytes = new Uint8Array(buffer);
                    let binary = '';
                    bytes.forEach((b) => binary += String.fromCharCode(b));

                    const blob = fileLoader.b64StringToBlob(binary);
                    const blobUrl = URL.createObjectURL(blob);

                    fileLoader.setSrcAttribute(blobUrl, fileName, id);
                });
            };

            connector.fetchTemplateWithoutBody(`/api/articles/${id}/file`, connector.GET, loadFile);
        };


        //todo loadMediaFile과 중복!
        const loadProfileImageFile = (fileLoader, userId, className) => {
            const connector = FETCH_APP.FetchApi();

            const loadFile = response => {
                response.arrayBuffer().then(buffer => {
                    if (buffer.byteLength === 0) {
                        return;
                    }

                    const bytes = new Uint8Array(buffer);
                    let binary = '';
                    bytes.forEach((b) => binary += String.fromCharCode(b));

                    const blob = fileLoader.b64StringToBlob(binary);
                    const blobUrl = URL.createObjectURL(blob);


                    fileLoader.setProfileSrcAttribute(blobUrl, className + userId);

                });
            };

            connector.fetchTemplateWithoutBody(`/api/users/${userId}/image`, connector.GET, loadFile);
        };

        return {
            b64StringToBlob: b64StringToBlob,
            setSrcAttribute: setSrcAttribute,
            setProfileSrcAttribute: setProfileSrcAttribute,
            loadMediaFile: loadMediaFile,
            loadProfileImageFile: loadProfileImageFile
        }
    };
    return {
        FileLoadService: FileLoadService
    }
})();