import EventAbstract from "./EventAbstract";
import $ from 'jquery';
import {OverrideRequiredError} from "../others/CustomErrors";


export default function ElementAbstract(){
  EventAbstract.call(this);
  this.$element = null;
}
ElementAbstract.prototype = new Object(EventAbstract.prototype);
ElementAbstract.constructor = ElementAbstract;

ElementAbstract.prototype.getElement = function(){
  return this.$element;
}
ElementAbstract.prototype.createElement = function(){
  throw new OverrideRequiredError('createElement');
}
ElementAbstract.prototype.removeElement = function(){
  $(this.$element).off();
  $(this.$element).remove();
}
ElementAbstract.prototype.draw = function(){
  throw new OverrideRequiredError("draw");
}
