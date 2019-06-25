package com.sysu.ceres.http;

import com.sysu.ceres.model.MessageList;
import com.sysu.ceres.model.QuestionList;
import com.sysu.ceres.model.Status;
import com.sysu.ceres.model.Survey;
import com.sysu.ceres.model.SurveyFull;
import com.sysu.ceres.model.SurveyList;
import com.sysu.ceres.model.TaskList;

import io.reactivex.Observable;
import io.reactivex.Observer;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ApiMethods {

    /**
     * 封装线程管理和订阅的过程
     */
    public static void ApiSubscribe(Observable observable, Observer observer) {
        observable.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    /**
     * 用于获取任务列表
     *
     * @param observer 由调用者传过来的观察者对象
     */
    public static void getTaskList(Observer<TaskList> observer) {
        ApiSubscribe(Api.getApiService().getTaskList(false), observer);
    }

    public static void getTaskListByDDL(Observer<TaskList> observer, String mode) {
        ApiSubscribe(Api.getApiService().getTaskListByDDL(mode, false), observer);
    }

    public static void getTaskListByMoney(Observer<TaskList> observer, String mode) {
        ApiSubscribe(Api.getApiService().getTaskListByMoney(mode, false), observer);
    }
    public static void getTaskListByStartTime(Observer<TaskList> observer, String mode) {
        ApiSubscribe(Api.getApiService().getTaskListByStartTime(mode, false), observer);
    }

    public static void getMessageList(Observer<MessageList> observer, int tid) {
        ApiSubscribe(Api.getApiService().getMessageList(tid), observer);
    }

    public static void addQuestion(Observer<Status> observer, long sid, long qid, String qtype, String qtitle, String answer_a, String answer_b, String answer_c, String answer_d) {
        ApiSubscribe(Api.getApiService().addQuestion(sid, qid, qtype, qtitle, answer_a, answer_b, answer_c, answer_d), observer);
    }

    public static void getSurveyList(Observer<SurveyList> observer, long tid) {
        ApiSubscribe(Api.getApiService().getSurveyList(tid), observer);
    }

    public static void getQuestionList(Observer<SurveyFull> observer, long sid) {
        ApiSubscribe(Api.getApiService().getQuestionList(sid), observer);
    }

    public static void updateAnswers(Observer<Status> observer, long sid, long qid, String answer) {
        ApiSubscribe(Api.getApiService().updateAnswers(sid, qid, answer), observer);
    }

}