import BsModal from 'bootstrap/js/dist/modal';
export default function Modal(){
    this.events = [];
    this.$modal = this.createElement();
    this.$bs_modal = new BsModal(this.$modal);
    this.draw();
}

Modal.prototype.createElement = function(){
    return $("");
}
Modal.prototype.open = function(){
    this.$bs_modal.show();
    this.emit('open');
}
Modal.prototype.close = function(){
    this.$bs_modal.hide();
    this.emit('close');
}
Modal.prototype.addEventListener = function(event_name,callback){
    this.events.push([event_name,callback]);
}
Modal.prototype.removeEventListener = function(event_name){
    this.events = this.events.filter(event=>event[0] !== event_name);
}
Modal.prototype.emit = function(...args){
    console.log('emit');
    queueMicrotask(()=>{
        if(args.length === 0)
            throw new Error('no arguments emit..');
        const event_name = args[0];
        const return_args = args.toSpliced(0,1);
        this.events.forEach(event =>{
            if(event[0] === event_name)
                event[1](...return_args);
        })
    });
}
Modal.prototype.draw = function(){
    $("body").after(this.$modal);
}
