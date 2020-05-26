package com.flagwind.persistent.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 复合短语
 *
 * @author chendb
 */
public class CombineClause extends ArrayList<Clause> implements Clause {

    private static final long serialVersionUID = 7081878195945581519L;

    private ClauseCombine combine;

    public CombineClause(ClauseCombine combine) {
        this.combine = combine;
    }

    public CombineClause(ClauseCombine combine, Collection<Clause> clauses) {
        super(clauses.stream().filter(g -> g != null).collect(Collectors.toList()));
        this.combine = combine;
    }

    public ClauseCombine getCombine() {
        return combine;
    }

    public void setCombine(ClauseCombine clauseCombine) {
        this.combine = clauseCombine;
    }

    // region 链式方法

    public CombineClause join(Clause... clauses) {
        if (clauses == null) {
            return this;
        }
        for (Clause clause : clauses) {
            if (clause != null) {
                this.add(clause);
            }
        }
        return this;
    }

    // endregion

    // region 静态构造方法

    /**
     * 构建and组合条件
     */
    public static CombineClause and(Clause... clauses) {
        List<Clause> list = Arrays.asList(clauses).stream().filter(g -> g != null).collect(Collectors.toList());
        return new CombineClause(ClauseCombine.And, list);
    }

    /**
     * 构建or组合条件
     */
    public static CombineClause or(Clause... clauses) {
        List<Clause> list = Arrays.asList(clauses).stream().filter(g -> g != null).collect(Collectors.toList());
        return new CombineClause(ClauseCombine.Or, list);
    }

    // endregion

}
