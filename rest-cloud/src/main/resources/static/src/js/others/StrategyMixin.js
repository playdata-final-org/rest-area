import Strategy from "../abstract/StrategyAbstract";
import {OverrideRequiredError} from "./CustomErrors";


/**
 * Key Value
 * @typedef {Object} strategies
 * @property {string} key
 * @property [function] strategy
 */

/**
 * Strategy 설정을 위한 믹스인
 * @mixin
 */
export const StrategyMixin = {
  /**
   * @param {strategies} strategies
   */
  setStrategies: function (strategies) {
    let _this = this;
    if(!_this.hasOwnProperty('STRATEGY_TYPE')){
      throw new Error('prototype.STRATEGY_TYPE is must be required');
    }
    _this.strategies = {};
    if(typeof strategies !== 'object')
      throw new TypeError('strategies must be object type');

    for(let key in strategies){
      if (this.isInstanceOf(strategies[key])){
        _this.strategies[key] = strategies[key];
      }else{
        throw new Error(`${key} is not instance of strategy`);
      }

    }

  },
  /**
   * instance가 strategy 하위 객체인지 확인하는 용도이며,
   * 각 클래스에서 prototype.STRATEGY_TYPE 재정의가 필요함.
   * 하위 클래스에서 재정의
   * 외부에서 호출하지 말것.
   * @param _this
   * @param strategy
   * @returns {boolean}
   */
  isInstanceOf: function (strategy) {
    return strategy instanceof this.STRATEGY_TYPE;
  },
};

