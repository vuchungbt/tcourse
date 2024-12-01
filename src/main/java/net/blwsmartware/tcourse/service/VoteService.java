package net.blwsmartware.tcourse.service;

import net.blwsmartware.tcourse.entity.Vote;

public interface VoteService {
    Vote addVote(Long postId, Long userId, int stars);
    Vote getByID(long id);
    void delete(long id);
}
