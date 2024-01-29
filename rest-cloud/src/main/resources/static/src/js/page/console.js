
import ProjectDropDown from "../components/ProjectDropDown";
import {OverrideRequiredError} from "../others/CustomErrors";

window.addEventListener('load',()=>{
  let dropdown_selector = `[name=project-dropdown]`;

  try {
    let $project_drop_down = new ProjectDropDown('[name=project-dropdown]');
    $project_drop_down.setStrategies();
  }catch(e){
    if(e instanceof OverrideRequiredError){
      console.error('OverrideRequire Error');
    }else{

    }

  }


  // // $project_add_modal.open();
  // $project_add_modal.addEventListener('save',(value_obj) =>{
  //     let {value} = value_obj;
  //     console.log(value);
  // })

});


