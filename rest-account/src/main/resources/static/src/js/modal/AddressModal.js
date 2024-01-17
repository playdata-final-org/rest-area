import Modal from './Modal.js';

export default function AddressModal(){
    Modal.call(this);
    this.$save_btn = $(this.$modal).find(".save");
    this.elementRegistEvent();
}

AddressModal.prototype = Object.create(Modal.prototype);
AddressModal.constructor = AddressModal;
AddressModal.prototype.elementRegistEvent = function(){
    let _this = this;
    this.$save_btn.on("click",()=>{
        this.emit('save');
        this.$bs_modal.hide();
    });
}
AddressModal.prototype.open = function(){
    Modal.prototype.open.call(this);
    console.log('open');
}
AddressModal.prototype.createElement = function(){
    return $(`
        <div class="modal fade " id="name-modal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
            <div class="modal-dialog modal-dialog-centered">
              <div class="modal-content"> 
                <div class="modal-header d-none d-md-flex border-bottom-0">
                  <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body ">
                  <div class="row">
                    <div class="col-12 col-md-2">
                        <p>우편번호</p>
                    </div>
                    <div class="col-12 col-md-10">
                        <input type="number" min="10000" max="99999" class="form-control" placeholder="우편번호 숫자 5자리">
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
