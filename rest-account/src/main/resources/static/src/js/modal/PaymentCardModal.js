import Modal from './Modal.js';

export default function PaymentCardModal(){
    Modal.call(this);
    this.$save_btn = $(this.$modal).find(".save");
    this.elementRegistEvent();
}

PaymentCardModal.prototype = Object.create(Modal.prototype);
PaymentCardModal.constructor = PaymentCardModal;
PaymentCardModal.prototype.elementRegistEvent = function(){
    let _this = this;
    this.$save_btn.on("click",()=>{
        this.emit('save');
        this.$bs_modal.hide();
    });
}
PaymentCardModal.prototype.open = function(){
    Modal.prototype.open.call(this);
    console.log('open');
}
PaymentCardModal.prototype.createElement = function(){
    return $(`
        <div class="modal fade " id="name-modal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
            <div class="modal-dialog modal-dialog-centered">
              <div class="modal-content"> 
                <div class="modal-header d-none d-md-flex border-bottom-0">
                  <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body ">
                  <div class="row mb-3">
                    <div class="col-12 col-sm-3 mb-1">
                        <label for="restore-email" class="form-label mb-0 mt-1">카드번호</label>
                    </div>
                    <div class="col-12 col-sm-9 mb-3 row mx-0">
                        <input type="number" name="card-number" min="0" max="9999" class="px-0 mx-1 text-center form-control d-inline col">
                     
                        <input type="number" name="card-number" min="0" max="9999" class="px-0 mx-1 ext-center form-control d-inline col">
                     
                        <input type="number" name="card-number" min="0" max="9999" class="px-0 mx-1 text-center form-control d-inline col">
                     
                        <input type="number" name="card-number" min="0" max="9999" class="px-0 mx-1 text-center form-control d-inline col">
                    </div>
                    <div class="col-12 col-sm-3 mb-1">
                        <label for="restore-email" class="form-label text-center mb-0 mt-1">CVC</label>
                    </div>
                    <div class="col-12 col-sm-3 mb-3  mx-0">
                        <input type="number" name="cvc-number" class="form-control px-1" min="0" max="999" placeholder="숫자 3자리">
                    </div>
                    <div class="col-12 col-sm-3 mb-1">
                        <label for="restore-email" class="form-label mb-0 mt-1">유효기간</label>
                    </div>
                    <div class="col-12 col-sm-3 mb-0  mx-0">
                        <input type="text" name="card-number" class="form-control px-1" placeholder="MM/YY">
                    </div>
                    <div class="col-12">
                        <p>
                            <span class="desc-text">RestArea에서 제공하는 서비스에서 사용될 결제정보입니다.</span>
                        </p>
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
