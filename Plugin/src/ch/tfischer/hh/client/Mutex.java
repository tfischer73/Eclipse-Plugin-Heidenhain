package ch.tfischer.hh.client;

import org.eclipse.core.runtime.jobs.ISchedulingRule;



public class Mutex implements ISchedulingRule {

    public boolean contains(ISchedulingRule rule) {
        return (rule == this);
    }

    public boolean isConflicting(ISchedulingRule rule) {
        return (rule == this);
    }

}// Mutex
