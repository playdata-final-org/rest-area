function downloadAttachment() {
  var mailId = $(this).data("mail-id");
  var attachmentName = $(this).data("attachment-name");

  // AJAX 요청을 통해 첨부 파일 다운로드 수행
  $.ajax({
    url: "/download/" + mailId,
    type: "GET",
    dataType: "binary", // dataType을 'binary'로 설정
    success: function (data) {
      // Blob 데이터를 다운로드할 수 있는 링크로 만들어서 클릭
      var url = window.URL.createObjectURL(new Blob([data]));
      var a = document.createElement("a");
      a.href = url;
      a.download = attachmentName;
      document.body.appendChild(a);
      a.click();
      document.body.removeChild(a);
    },
  });
}
