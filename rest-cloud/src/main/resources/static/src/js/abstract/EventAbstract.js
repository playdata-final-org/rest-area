import {ParamRequiredError} from "../others/CustomErrors";


export default function EventAbstract() {
  this.events = [];
}
/**
 * 이벤트 등록
 * @param {string} event_name
 * @param {Function} callback
 * @returns {EventAbstract}
 */
EventAbstract.prototype.addEventListener = function(event_name,callback){
  this.events.push([event_name,callback]);
  return this;
}
/**
 * 이벤트 제거
 * @param event_name
 * @returns {EventAbstract}
 */
EventAbstract.prototype.removeEventListener = function(event_name){
  this.events = this.events.filter(event=>event[0] !== event_name);
  return this;
}
/**
 * 이벤트 emit
 * 1번째 인자값으로는 이벤트 명이 전달되어야 하며, 이후 callback을 통해 전달할 인자들을 전달해야한다.
 * @param {string} event_name
 * @param args
 */
EventAbstract.prototype.emit = function(event_name,...args){
  return queueMicrotask(()=>{
    if(!event_name)
      throw new ParamRequiredError('event_name');
    // if(args.length === 0)
    //   throw new ParamRequiredError('no arguments emit..');
    this.events.forEach(event =>{
      if(event[0] === event_name)
        event[1](...args);
    })
  });
}
