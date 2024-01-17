import Modal from './Modal.js';

export default function PasswordModal(){
    Modal.call(this);
    this.$save_btn = $(this.$modal).find(".save");
    this.elementRegistEvent();
}

PasswordModal.prototype = Object.create(Modal.prototype);
PasswordModal.constructor = PasswordModal;
PasswordModal.prototype.elementRegistEvent = function(){
    let _this = this;
    this.$save_btn.on("click",()=>{
        this.emit('save');
        this.$bs_modal.hide();
    });
}
PasswordModal.prototype.open = function(){
    Modal.prototype.open.call(this);
    console.log('open');
}
PasswordModal.prototype.createElement = function(){
    return $(`
        <div class="modal fade " id="name-modal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
            <div class="modal-dialog modal-dialog-centered">
              <div class="modal-content"> 
                <div class="modal-header d-none d-md-flex border-bottom-0">
                  <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body ">
                  <div class="row mb-2">
                    <div class="col-12 col-md-4 mb-1">
                        <p class="m-0">현재 비밀번호</p>
                    </div>
                    <div class="col-12 col-md-8">
                        <input type="password" class="form-control">
                    </div>
                  </div>
                <div class="row mb-2">
                    <div class="col-12 col-md-4  mb-1">
                        <p class="m-0">새 비밀번호</p>
                    </div>
                    <div class="col-12 col-md-8">
                        <input type="password" class="form-control">
                    </div>
                  </div>
                <div class="row mb-2">
                    <div class="col-12 col-md-4  mb-1">
                        <p class="m-0">비밀번호 재입력</p>
                    </div>
                    <div class="col-12 col-md-8">
                        <input type="password" class="form-control">
                    </div>
                  </div>
                </div>
                <div class="modal-footer border-top-0">
                  <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">닫기</button>
                  <button type="button" class="btn btn-primary save">저장</button>
                </div>
              </div>
            </div>
    `);
}
