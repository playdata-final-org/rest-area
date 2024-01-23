export function ParamRequiredError(args_name){
  this.name = 'ParamRequiredError';
  this.message = `${args_name} is required parameter..`;
}
ParamRequiredError.prototype = new Object(Error.prototype);
ParamRequiredError.constructor = ParamRequiredError;

export function OverrideRequiredError(function_name){
  this.name = 'OverrideRequiredError';
  this.message = `${function_name} is must be override`;
}
OverrideRequiredError.prototype = new Object(Error.prototype);
OverrideRequiredError.constructor = OverrideRequiredError;


export default { ParamRequiredError,OverrideRequiredError };




