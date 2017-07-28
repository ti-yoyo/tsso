package com.tinet.tsso.auth.dao;

/**
 * 
 * @author 侯法超
 *
 * @param <T>
 * @param <ID>
 */
public interface BaseMapper<T, ID> {

	/**
	 * 保存（持久化）对象,对字段选择性保存，当某字段为null时，此字段不保存
	 * 
	 * @param o
	 *            要持久化的对象
	 * @return 执行成功的记录个数
	 */
	Integer insertSelective(T o);

	/**
	 * 更新（持久化）对象
	 * 
	 * @param o
	 *            要持久化的对象
	 * @return 执行成功的记录个数
	 */
	Integer updateByPrimaryKey(T o);

	/**
	 * 更新（持久化）对象,对字段选择性更新，当某字段为null时，此字段不更新
	 * 
	 * @param o
	 *            要持久化的对象
	 * @return 执行成功的记录个数
	 */
	Integer updateByPrimaryKeySelective(T ob);

	/**
	 * 获取指定的唯一标识符对应的持久化对象
	 *
	 * @param id
	 *            指定的唯一标识符
	 * @return 指定的唯一标识符对应的持久化对象，如果没有对应的持久化对象，则返回null。
	 */
	T selectByPrimaryKey(ID id);

	/**
	 * 删除指定的唯一标识符对应的持久化对象
	 *
	 * @param id
	 *            指定的唯一标识符
	 * @return 删除的对象数量
	 */
	Integer deleteByPrimaryKey(ID id);

}
