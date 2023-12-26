window.addEventListener("load",async function(){
    // ajax 최초 요청
    let response_data = null;
    var curPage = 1;
    var cutPage = 20;
    var keyword = "";
    var searchType = "";
    printView()

    function error_run(obj,resmsg,errormsg) {
        console.log("오류발생");
        console.log("obj = " + obj);
        console.log("resmsg = " + resmsg);
        console.log("errormsg = " + errormsg);
    }

    /* 검색 대상을 정하는 버튼의 전체 버튼을 눌렀을때 */
    $("#feed-selected-all").on("click", () => {
        if ($("#feed-selected-all").hasClass("selected")) {
            // 이미 선택된 상태인 경우, 클래스 제거
            $("#feed-selected-all").removeClass("selected");
        } else {
            // 선택되지 않은 상태인 경우, 클래스 추가
            $("#feed-selected-all").addClass("selected");
            $("#feed-selected-following").removeClass("selected"); // 다른 버튼 클래스 제거
        }
    });

    /* 검색 대상을 정하는 버튼의 친구만 버튼을 눌렀을때 */
    $("#feed-selected-following").on("click", () => {
        // 버튼의 "selected" 클래스를 토글
        if ($("#feed-selected-following").hasClass("selected")) {
            // 이미 선택된 상태인 경우, 클래스 제거
            $("#feed-selected-following").removeClass("selected");
        } else {
            // 선택되지 않은 상태인 경우, 클래스 추가
            $("#feed-selected-following").addClass("selected");
            $("#feed-selected-all").removeClass("selected");
        }
    });

    $("#feed-search").on("click", () => {
        $("#feed-auto-complete")[0].classList.add("cs-autocomplete-show");
    });
    $("#feed-search").on("blur", () => {
        $("#feed-auto-complete").removeClass("cs-autocomplete-show");
    });

    $("button[data-id='feed-btn']").addClass("disabled");
    let index = -1;
    const ARROW_UP = 38;
    const ARROW_DOWN = 40;
    const ENTER = 13;
    $("#feed-search").on("keydown", (e) => {
      let item_lth = $(".cs-autocomplete-item").length;
      switch (e.keyCode) {
        case ARROW_DOWN:
          if (index < item_lth) index++;
          $(".cs-autocomplete-item")[index].classList.add("active");
          e.target.value = $(".cs-autocomplete-item")[index].innerText;
          $(".cs-autocomplete-item")[index - 1].classList.remove("active"); // 추후 오류 처리 필요
          break;
        case ARROW_UP:
          if (index > 0) index--;
          $(".cs-autocomplete-item")[index].classList.add("active");
          e.target.value = $(".cs-autocomplete-item")[index].innerText;
          $(".cs-autocomplete-item")[index + 1].classList.remove("active"); // 추후 오류 처리 필요
          break;
        case ENTER:
            resetData();
            printView()
            document.body.focus();
            $(".cs-autocomplete-item").removeClass("active");
            $("#feed-auto-complete").removeClass("cs-autocomplete-show");
            break;
      }
      console.log(e.keyCode);
    });

    $("#search-button").on("click", () => {
        resetData();
        printView()
        document.body.focus();
        $(".cs-autocomplete-item").removeClass("active");
        $("#feed-auto-complete").removeClass("cs-autocomplete-show");
    });

    $(".cs-autocomplete-item").on("click", (e) => {
      console.log(e, e.target.innerText);
      $("#feed-search").val(e.target.innerText);
      $(".cs-autocomplete-item").removeClass("active");
      $("#feed-auto-complete").removeClass("cs-autocomplete-show");
    });



    async function printView() {
        keyword = $("#feed-search").val();
        console.log(keyword)
        if ($("#feed-selected-following").hasClass("selected")) {
            searchType = "following"
        } else if ($("#feed-selected-following").hasClass("selected")) {
            searchType = "all"
        } else {
            searchType = "all"
        }
        param = {curPage : curPage, cutPage : cutPage, keyword : keyword, searchtype : searchType}
        try{
                    let res =  await axios.get("/api/content/feed", {params : param})
                    let html = res.data.map(row => {
                        const {review_seq, title, name, view_count, star, img_1, created_at} = row;
                        let star_arr = new Array(star).fill(0);
                        let star_str = star_arr.map(i=>`<i class="fa-solid fa-star star-active fa-xs"></i>`).join('');
                        return `
                          <div class="col mb-2" OnClick="location.href ='/content/feed/${review_seq}'">
                          <div class="card cs-feed-card">
                          <img
                          src="/download/${img_1}"
                          onerror="this.src='/asset/no-image.png'" 
                          class="card-img-top img-fluid"
                          alt=""
                          />
                  <div class="card-body p-3">
                  <p class="card-text">
                  <span class="title"><b>${title}</b></span>
          <br />
          <span class="categroy-text">양식, 멕시코</span>
          <span class="stars">
                 ${star_str}
                    <span class="evaluation-count">(7)</span>
                  </span>
          <br />
          <span class="cs-start-user"><b>${name}</b></span>
          <br />
        </p>
          <div class="cs-tags mb-3">
            <button
                    class="btn btn-tag btn-sm btn-rounded border cs-tag cs-more-tag"
                    data-mdb-close="true"
            >
              ⚽️ 모임하기 좋아요
            </button>

            <button
                    class="btn btn-tag btn-sm btn-rounded border cs-tag cs-more-tag"
                    data-mdb-close="true"
            >
              ⚽️ 모임하기 좋아요
            </button>
            <button
                    class="btn btn-tag btn-sm btn-rounded border cs-tag cs-more-tag"
                    data-mdb-close="true"
            >
              ⚽️ 모임하기 좋아요
            </button>
            <button
                    class="btn btn-tag btn-sm btn-rounded border cs-tag cs-more-tag"
                    data-mdb-close="true"
            >
              ⚽️ 모임하기 좋아요
            </button>
          </div>
        </div>
        </div>
        </div>`    }).join('');
            $('#feed_list').append(html);


        } catch (e){
    console.log(e);
        }
    }
    function resetData() {
        $('#feed_list').empty(); // 기존 데이터 초기화
        curPage = 1; // 페이지 번호 초기화
    }

});