import ElementAbstract from "../abstract/ElementAbstract";
import {ParamRequiredError} from "../others/CustomErrors";
import StrategyAbstract from "../abstract/StrategyAbstract";
import {StrategyMixin} from "../others/StrategyMixin";


/**
 * ListParams
 * @typedef {Object} list_params
 * @property {string} label_id
 * @property [{key : string, value : string}=] data - list data
 * @property {'Y' | 'N'} [add_item_yn='N']
 */

/**
 * @param {list_params} options
 * @param { import("../abstract/StrategyAbstract.js").strategies} strategies
 */
export default function DropDownList(options,strategies){
  ElementAbstract.call(this);
  this.$element = this.createElement();
  if(!options.hasOwnProperty('label_id'))
    throw new ParamRequiredError('label_id');
  this.setStrategies(strategies);

  this.label_id = options?.label_id;
}

DropDownList.prototype = new Object(ElementAbstract.prototype);
DropDownList.constructor = DropDownList;
Object.assign(DropDownList.prototype,StrategyMixin);
DropDownList.prototype.STRATEGY_TYPE =


DropDownList.prototype.elementRegistEvent = function(){
  this.$element.on("click",(e)=>{



  });
}
DropDownList.prototype.getData = function(){

}
DropDownList.prototype.createElement = function(){
  return $(`
              <ul class="dropdown-menu" aria-labelledby="${this.label_id}">
                <li><a class="dropdown-item active" href="#">project1</a></li>
                <li><a class="dropdown-item">testproject</a></li>
                <li><a class="dropdown-item">+ 프로젝트 등록</a></li>
              </ul>
  `)
}




function DefaultDropDownStrategies(){

    function Active(){

    }
    Active.prototype = new Object(StrategyAbstract.prototype);
    Active.constructor = Active;
    function GetData(){

    }
    GetData.prototype = new Object(StrategyAbstract.prototype);
    GetData.constructor = GetData;
    function Render(){

    }
    Render.prototype = new Object(StrategyAbstract.prototype);
    Render.constructor = Render;

    return {


    }
}



export { DropDownList,DefaultDropDownStrategies };
