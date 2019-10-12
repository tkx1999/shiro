package com.it.activiti.activiti.service;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;

import java.util.List;


public class HelloworldProcess {
    //获取默认流程引擎，自动读取activity.cfg.xml文件
  private ProcessEngine processEngine= ProcessEngines.getDefaultProcessEngine();

  public void deploy(){
      System.out.println("进入流程部署");
     Deployment deployment= processEngine.getRepositoryService()//可以获取部署相关service
      .createDeployment()//创建部署
      .addClasspathResource("processes/test.bpmn")//加载资源文件
      .name("helloworld")
      .deploy();//部署

      System.out.println("流程部署ID:"+deployment.getId());
      System.out.println("流程部署Name:"+deployment.getName());
  }
    /**
     * 启动流程实例
     */
  public void  start(){
   ProcessInstance processInstance= processEngine.getRuntimeService()//运行流程实例
      .startProcessInstanceByKey("leven");//流程定义表里的KEY
      System.out.println("流程实例ID:"+processInstance.getId());
      System.out.println("流程部署ID："+processInstance.getProcessDefinitionId());
  }
  //查看任务
  public void findTask(){
   List<Task> taskList= processEngine.getTaskService()//任务相关
      .createTaskQuery()//创建任务查询
      .taskAssignee("sudent")//指定一个人
      .list();
   for (Task task:taskList){
       System.out.println("任务ID:"+task.getId());
       System.out.println("任务Name:"+task.getName());
       System.out.println("流程实例id:"+task.getName());
   }
  }
//完成任务
  public void completeTask(){
      processEngine.getTaskService()//相关任务
      .complete("5005");
  }
}
