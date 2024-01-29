import Modal from '../abstract/ModalAbtract.js';
import validator from 'validator';
import StrategyAbstract from "../abstract/StrategyAbstract";
import {StrategyMixin} from "../others/StrategyMixin";
import EventAbstract from "../abstract/EventAbstract";

export default function ProjectAddModal(selector){
    Modal.call(this);
    this.$save_btn = $(this.$modal).find(".save");
    this.$project_name_input = $(this.$modal).find("input:text");
    this.elementRegistEvent();
}

ProjectAddModal.prototype = Object.create(Modal.prototype);
ProjectAddModal.constructor = ProjectAddModal;


ProjectAddModal.prototype.elementRegistEvent = function(){
    this.$save_btn.on("click",()=>{
        this.emit('save',{
          value : this.$project_name_input.val()
        });
        this.$bs_modal.hide();
    });
    this.$project_name_input.on("keyup",()=>{

    })
}
ProjectAddModal.prototype.open = function(){
    this.$project_name_input.val('');
    Modal.prototype.open.call(this);
}
ProjectAddModal.prototype.close = function(){
    this.emit('close');
}
ProjectAddModal.prototype.createElement = function(){
    return $(`
        <div class="modal fade " id="name-modal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
            <div class="modal-dialog modal-dialog-centered">
              <div class="modal-content"> 
                <div class="modal-header d-none d-md-flex border-bottom-0">
                  <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body ">
                  <div class="row">
                    <div class="col-12 col-md-3">
                        <p>프로젝트명</p>
                    </div>
                    <div class="col-12 col-md-9">
                        <input type="text" class="form-control">
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

