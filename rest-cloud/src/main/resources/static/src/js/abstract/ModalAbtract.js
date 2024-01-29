import BsModal from 'bootstrap/js/dist/modal';
import ElementAbstract from "./ElementAbstract";
import $ from 'jquery';
export default function ModalAbtract(){
  ElementAbstract.call(this);
    this.$modal = this.createElement();
    this.$bs_modal = new BsModal(this.$modal);
    this.draw();
}
ModalAbtract.prototype = Object.create(ElementAbstract.prototype);
ModalAbtract.constructor = ModalAbtract;

ModalAbtract.prototype.open = function(){
    this.$bs_modal.show();
    this.emit('open');
}
ModalAbtract.prototype.close = function(){
    this.$bs_modal.hide();
    this.emit('close');
}

ModalAbtract.prototype.draw = function(){
    $("body").after(this.$modal);
}
