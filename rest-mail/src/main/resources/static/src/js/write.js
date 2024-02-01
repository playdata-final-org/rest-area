window.onload = function(){
$("input").on("keyup",(e)=>{
  e.preventDefault();
console.log(e)
  if(e.keyCode == 13)
    return;
})

var input1 = document.querySelector('#recipient');
var input2 = document.querySelector('#cc');
var tagify1 = new Tagify(input1);
var tagify2 = new Tagify(input2);

// 태그가 추가되면 이벤트 발생
tagify1.on('add', function() {
  console.log(tagify1.value);
})
tagify2.on('add', function() {
  console.log(tagify2.value);
})

$("#btnSubmit").click(function (event) {
	//preventDefault 는 기본으로 정의된 이벤트를 작동하지 못하게 하는 메서드이다. submit을 막음
	event.preventDefault();
    // Get form
    var form = $('#fileUploadForm')[0];
    // Create an FormData object
    var data = new FormData(form);
    data.append('recipient',tagify1.value.map(i=>{return i?.value}));
    data.append('cc', tagify2.value.map(i=>{return i?.value}));
    // disabled the submit button
    $("#btnSubmit").prop("disabled", true);

    $.ajax({
    	type: "POST",
        enctype: 'multipart/form-data',
        url: "/mail/send",
        data: data,
        processData: false,
        contentType: false,
        cache: false,
        timeout: 600000,
        success: function (data) {
        	location.href ="/mailComplete"
        	$("#btnSubmit").prop("disabled", false);
        },
        error: function (e) {
        	console.log("ERROR : ", e);
            $("#btnSubmit").prop("disabled", false);
            alert("오류");
         }
	});
});
}
