package com.flagwind.io;

/**
 * 表示关于路径的锚定点
 */
public enum PathAnchor {
    /**
     * 未锚定
     */
    None,

    /**
     * 基于当前位置
     */
    Current,

    /**
     * 基于上级节点
     */
    Parent,

    /**
     * 从根节点开始
     */
    Root,
}