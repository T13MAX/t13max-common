package com.t13max.ai.nodes;


import com.t13max.ai.btree.attachments.PreActionNode;

/**
 * @Author t13max
 * @Date 13:51 2024/5/23
 */
public class TestPreconditionFail<E> extends PreActionNode<E> {

    @Override
    public boolean preCondition() {
        return false;
    }
}