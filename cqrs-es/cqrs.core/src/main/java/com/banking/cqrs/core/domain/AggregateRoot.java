package com.banking.cqrs.core.domain;

import com.banking.cqrs.core.events.BaseEvent;
import lombok.Data;

import java.lang.reflect.Method;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Data
public class AggregateRoot {
    protected  String id;
    private int version = -1;

    private final List<BaseEvent> changes = new ArrayList<>();

    private final Logger logger = Logger.getLogger(AggregateRoot.class.getName());

    public List<BaseEvent> getUncommitedChanges(){
        return this.changes;
    }

    public void markChangesAsCommited(){
        this.changes.clear();
    }

    protected  void applyChange(BaseEvent event, Boolean isNewEvent){
        try{
            Method method = getClass().getDeclaredMethod("apply", event.getClass());
            method.setAccessible(true);
            method.invoke(this, event);
        }catch (NoSuchMethodException e){
            logger.log(Level.WARNING, MessageFormat.format("el methodo apply no fue encontrado para {0}", event.getClass()));
        }catch (Exception e){
            logger.log(Level.SEVERE, MessageFormat.format("Errores aplicando el methodo {0}", event.getClass()));
        }finally {
            if(isNewEvent){
                changes.add(event);
            }
        }
    }

    public void raiseEvent(BaseEvent event){
        applyChange(event, true);
    }

    public void replayEvents(Iterable<BaseEvent> events){
        events.forEach(event -> applyChange(event,false));
    }
}
