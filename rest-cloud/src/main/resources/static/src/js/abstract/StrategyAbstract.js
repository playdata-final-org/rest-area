import {OverrideRequiredError} from "../others/CustomErrors";


export default function StrategyAbstract(){

}
StrategyAbstract.prototype.execute = function(){
  throw new OverrideRequiredError("execute");
}


