package com.balazskreith.vstorage.raft.events;

import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.core.Scheduler;

public interface EventReceiver {
    static EventReceiver createFrom(Events events) {
        return new EventReceiver() {
            @Override
            public Observer<RaftVoteResponse> voteResponse() {
                return events.voteResponse();
            }

            @Override
            public Observer<RaftVoteRequest> voteRequests() {
                return events.voteRequests();
            }

            @Override
            public Observer<RaftAppendEntriesRequest> appendEntriesRequest() {
                return events.appendEntriesRequest();
            }

            @Override
            public Observer<RaftAppendEntriesResponse> appendEntriesResponse() {
                return events.appendEntriesResponse();
            }
        };
    }

    Observer<RaftVoteResponse> voteResponse();
    Observer<RaftVoteRequest> voteRequests();
    Observer<RaftAppendEntriesRequest> appendEntriesRequest();
    Observer<RaftAppendEntriesResponse> appendEntriesResponse();

    default EventReceiver observeOn(Scheduler scheduler) {
        var result = new Events();
        result.voteRequests().observeOn(scheduler).subscribe(this.voteRequests());
        result.voteResponse().observeOn(scheduler).subscribe(this.voteResponse());
        result.appendEntriesRequest().observeOn(scheduler).subscribe(this.appendEntriesRequest());
        result.appendEntriesResponse().observeOn(scheduler).subscribe(this.appendEntriesResponse());
        return EventReceiver.createFrom(result);
    }
}
