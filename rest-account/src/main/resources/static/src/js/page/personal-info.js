import NameModal from "../modal/NameModal";
import BirthModal from "../modal/BirthModal";
import GenderModal from "../modal/GenderModal";
import AddressModal from "../modal/AddressModal";
import AddressDetailModal from "../modal/AddressDetailModal";
import PasswordModal from "../modal/PasswordModal";


window.addEventListener('load',()=>{
    new App();
});

function App(){
    this.$profile_btn = $("#profile");
    this.$profile_img = $("#profile > img");
    this.$profile_file = $("input[type=file][data-target=profile]");
    this.$profile_save_btn = $("#profile-save");
    this.$open_name_modal_btn = $("#name");
    this.$open_birth_modal_btn = $("#birth");
    this.$open_gender_modal_btn = $("#gender");
    this.$open_password_modal_btn = $("#password");
    this.$open_address_modal_btn = $("#address");
    this.$open_address_detail_modal_btn = $("#address-detail");
    this.$open_modal_btns = $("[name=open-modal]"); // 모달을 열 수 있는 버튼들

    this.$name_modal = new NameModal();
    this.$birth_modal = new BirthModal();
    this.$gender_modal = new GenderModal();
    this.$password_modal = new PasswordModal();
    this.$address_modal = new AddressModal();
    this.$address_detail_modal = new AddressDetailModal();
    this.addEvent();
}
App.prototype.addEvent = function(){

    this.$open_modal_btns.each((idx,ele)=>{
        let id = ele.id;
        if(id) {
            id = id.replace('-', '_');
            this[`$open_` + `${id}_modal_btn`].on('click', () => {
                this[`$` + `${id}_modal`].open();
            });
        }
    })
    this.$profile_btn.on('click',()=>{
        this.$profile_file.click();
    });
    this.$profile_save_btn.on('click',()=>{
        this.$profile_save_btn.addClass('d-none');
    })
    this.$profile_file.on('change',(e)=>{
        let file = this.$profile_file[0].files[0];
        let reader = new FileReader();
        reader.addEventListener('load',()=>{
            console.log(this.$profile_img,reader);
            this.$profile_img.attr('src', reader.result);
            this.$profile_save_btn.removeClass('d-none');
        });
        if (file) {
            reader.readAsDataURL(file);
        }
    });

}

