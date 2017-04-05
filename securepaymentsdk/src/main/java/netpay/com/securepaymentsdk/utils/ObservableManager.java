package netpay.com.securepaymentsdk.utils;

import java.util.Observable;

/**
 * Created by AcheDev on 4/3/17.
 */

public class ObservableManager extends Observable {

    private static ObservableManager iObservableManager ;

    private ObservableManager(){

    }

    public static ObservableManager getInstance(){
        return iObservableManager;
    }

    public static ObservableManager newInstance() {
        if (iObservableManager != null)
            iObservableManager = null;
        return iObservableManager = new ObservableManager();
    }

    @Override
    public void notifyObservers() {
        setChanged();
        super.notifyObservers();
    }

    @Override
    public void notifyObservers(Object arg) {
        setChanged();
        super.notifyObservers(arg);
    }

    public enum NetPayEvents{
        _3DS_STARTED_LOAD,
        _3DS_FINISH_LOAD,
        _3DS_ERROR_LOAD,
        _3DS_FINISH_TRANSACTION;

        private String js;
        private String messageEvent;


        public String getJsonTransaction(){
            return js;
        }

        public void setJsonTransaction(String data){
            js = data;
        }

        public String getMessageEvent() {
            return messageEvent;
        }

        public void setMessageEvent(String messageEvent) {
            this.messageEvent = messageEvent;
        }
    }
}
