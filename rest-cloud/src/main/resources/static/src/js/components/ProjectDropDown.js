
import ProjectAddModal from "../modal/ProjectAddModal";
import EventAbstract from "../abstract/EventAbstract";
import {StrategyMixin} from "../others/StrategyMixin";
import StrategyAbstract from "../abstract/StrategyAbstract";
import { v4 as uuidv4 } from 'uuid';
import DropDownList from "./DropDownList";


export default function ProjectDropDown(selector){
    new DropDownList({
      id : '',
      add_item_yn : 'C',

    })
    if(!selector){
      throw new Error('selector is required parameter..');
    }
    this.selector = selector;
    this.$project_add_modal = new ProjectAddModal();
    this.$dropdown = this.createElement();
    this.draw();
    this.elementRegistEvent();
}

ProjectDropDown.prototype = Object.create(EventAbstract.prototype);
ProjectDropDown.constructor = ProjectDropDown;
ProjectDropDown.prototype.elementRegistEvent = function(){
  this.$dropdown.on("click",(e)=>{

    this.emit('click',e);
  });
}
Object.assign(ProjectDropDown.prototype,StrategyMixin);
ProjectDropDown.prototype.draw = function(){
  $(this.selector).html(this.$dropdown);
}
ProjectDropDown.prototype.createElement = function(){
    let uuid = uuidv4();
    console.log(uuid);
    return $(`
    <div>
     <button class="btn btn-primary border-light dropdown-toggle" type="button" id="${uuid}"
            data-bs-toggle="dropdown" aria-expanded="false">
        프로젝트 선택
    </button>
  
    </div>
    `);
}


export function DefaultProjectDropdownStrategy(){

}
DefaultProjectDropdownStrategy.prototype = new Object(StrategyAbstract.prototype);
DefaultProjectDropdownStrategy.constructor = DefaultProjectDropdownStrategy;
DefaultProjectDropdownStrategy.prototype.execute = function(){

}

