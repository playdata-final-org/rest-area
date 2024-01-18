import PaymentCardModal from "../modal/PaymentCardModal";


window.addEventListener('load',()=>{
    new App();
});

function App(){
    this.$open_payment_card_modal_btn = $("#payment-card");
    // this.$open_birth_modal_btn = $("#birth");

    this.$payment_card_modal = new PaymentCardModal();
    // this.$birth_modal = new BirthModal();
    this.addEvent();
}
App.prototype.addEvent = function(){

    this.$open_payment_card_modal_btn.on('click',()=>{
        this.$payment_card_modal.open();
    });

}