$(document).ready(function() {
    var currentPage = 1;


    $('.pagination').on('click', 'a.page-link', function(e) {
        e.preventDefault();
        currentPage = $(this).data('page');
        fetchPageData(currentPage);
    });

    // 이전 버튼 클릭 이벤트 핸들러
    $('#prevBtn').on('click', function() {
        if (currentPage > 1) {
            currentPage--;
            fetchPageData(currentPage);
        }
    });


    $('#nextBtn').on('click', function() {
        currentPage++;
        fetchPageData(currentPage);
    });


    function fetchPageData(page) {
        var url = '/blogs?page=' + page;
        $.ajax({
            type: 'GET',
            url: url,
            success: function(response) {
                displayBlogs(response)

            },
            error: function(err) {
                console.error('Error:', err);
            }
        });
    }
});

function displayBlogs(response) {
    console.log(response);
    var homeContainer = $('#homeContainer');
    homeContainer.empty();

    if (response.length === 0) {
        homeContainer.append('<p>컬렉션이 없습니다.</p>');
    } else {
        $.each(response, function(index, collection) {
            var historyTierId = collection.historyTierId;

            var collectionTierId = collection.collectionTierId;
            var membershipType = collection.membershipType;
            var collectionTierName = collection.collectionTierName;
            var collectionTitle = collection.collectionTitle;
            var collectionContent = collection.collectionContent;
            var collectionImagesUrls = collection.collectionImagesUrls;
            var collectionFileName = collection.collectionFileName;
            var collectionUuid = collection.collectionUuid;
            var collectionCreateDate = collection.collectionCreateDate;

            var collectionHTML =
                <th:block th:each="collection, iter : ${collections}">
                   <div class="card">

                       <div class="fistImageContainer" style="display:flex; justify-content: center;">
                           <th:block th:each="imageUrl, iterStat : ${collectionImagesUrlsList[iter.index]}">
                               <div th:classappend="${imageUrl != null}" style="width:300px; height:300px;">
                                   <img th:src="${imageUrl}" alt="Collection Image"
                                        style="width: 100%; height: 100%; object-fit: contain;">
                               </div>
                           </th:block>
                       </div>

                       <div class="card-body" style="display: flex; flex-direction: row; justify-content: space-between;">
                           <div>
                               <h2 class="card-title" style="font-size:30px;" th:text="${collection.title}">Title</h2>
                               <p class="card-text" th:text="${collection.createDate}">Date</p>
                               <p class="card-text" style="margin-top: 30px;" th:text="${collection.content}">Content</p>
                           </div>
                           <div>
                               <span th:text="${collection.tierName}" style="border-radius:10px; background: #c4c4db42; padding:10px;"></span>
                           </div>


                       </div>


                       <div class="cardFile" th:if="${collection.membershipType == 'allPublic' or tierId >= collection.tierId}"
                            style="background: #c4c4db42; width: 100%; padding: 20px; border-radius: 10px">
                           <p class="card-text">
                               <i class="fa fa-file-archive-o" aria-hidden="true" style="color: #0000ff;"></i>
                               <a th:if="${collectionFileNames[iter.index] != null}"
                                  th:href="@{/download/img/collectionFiles/{uuid}_{fileName}(uuid=${collectionUuids[iter.index]},fileName=${collectionFileNames[iter.index]})}"
                                  th:utext="${collectionFileNames[iter.index]}" style="color: #0000ff; margin-left: 10px;"></a>
                               <span th:unless="${collectionFileNames[iter.index] != null}">없음</span>
                           </p>
                       </div>

                       <div class="cardFile" th:unless="${collection.membershipType == 'allPublic' or tierId >= collection.tierId}"
                            style="background: #c4c4db42; width: 100%; padding: 20px; border-radius: 10px;
                                   display: flex; flex-direction: row; justify-content: space-between; margin-top: 10px;">
                          <div style="display: flex; align-items: center;">
                              <i class="fa fa-lock" aria-hidden="true"></i>
                              <p style="margin-left: 10px;"><span th:text="${collection.tierName}" style="margin-right: 10px;"></span>등급 이상 다운로드 가능합니다</p>
                          </div>
                           <div class="plan-about" style="background: #ffa500; padding: 15px; border-radius: 30px;">
                               <button style="color:#fff;background: transparent;border: transparent;">플랜 목록</button>
                           </div>
                       </div>
                       <script th:inline="javascript">
                           var blogId = /*[[${blog.blogId}]]*/
                           console.log(blogId)
                       </script>
                       <script src="/js/blog-collection-chooseMembership.js" type="text/javascript"></script>
                       <script src="/js/blog-collection-highLight.js" type="text/javascript"></script>
                   </div>
               </th:block>
            homeContainer.append(collectionHTML);
        });
    }
}