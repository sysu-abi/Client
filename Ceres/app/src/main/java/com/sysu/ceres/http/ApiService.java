package com.sysu.ceres.http;

import com.sysu.ceres.model.Message;
import com.sysu.ceres.model.MessageList;
import com.sysu.ceres.model.TaskList;

import io.reactivex.Observable;

import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiService {
//    创建任务：http://111.230.13.139:8080/ServerAndDB/createTask?uid=8&title=test&detail=abc&money=3&type=poll&total_num=2&end_time=2020-0101 00:00:00




    // 显示所有任务：http://111.230.13.139:8080/ServerAndDB/listTasks?extra=false
    @POST("listTasks")
    Observable<TaskList> getTaskList(@Query("extra") boolean extra);

//    按序显示所有任务：
//    http://111.230.13.139:8080/ServerAndDB/listTasksbyDDL?mode=desc&extra=false
//    http://111.230.13.139:8080/ServerAndDB/listTasksbyMoney?mode=desc&extra=false
//    http://111.230.13.139:8080/ServerAndDB/listTasksbyStartTime?mode=desc&extra=false
    @POST("listTasksbyDDL")
    Observable<TaskList> getTaskListByDDL(@Query("mode") String mode, @Query("extra") boolean extra);

    @POST("listTasksbyMoney")
    Observable<TaskList> getTaskListByMoney(@Query("mode") String mode, @Query("extra") boolean extra);

    @POST("listTasksbyStartTime")
    Observable<TaskList> getTaskListByStartTime(@Query("mode") String mode, @Query("extra") boolean extra);




//    获取某个任务下的所有留言：http://111.230.13.139:8080/ServerAndDB/listMessages?tid=3
    @POST("listMessages")
    Observable<MessageList> getMessageList(@Query("tid") int tid);


    // 获取该用户参与的所有任务：**http://111.230.13.139:8080/ServerAndDB/getJoinTasks?uid=1
    @POST("getJoinTasks")
    Observable<TaskList> getJoinTasks(@Query("uid") int uid);

}
