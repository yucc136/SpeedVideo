package com.ywh.dao.impl;

import org.hibernate.Query;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.ywh.dao.FileDao;
import com.ywh.entity.User;
import com.ywh.entity.Video;

public class FileDaoImpl extends HibernateDaoSupport implements FileDao {
	/**
	 * 保存视频文件信息
	 */
	public void save(Video video) {
		this.getHibernateTemplate().save(video);
	}

	/**
	 * 更新t_videolist表
	 */
	public void savetoVideolist(User user, Video video) {
		String sql = "insert into t_videolist values (:uid,:vid)";
		Query query = getSession().createSQLQuery(sql);
		query.setInteger("uid", user.getId());
		query.setInteger("vid", video.getId());
		query.executeUpdate();
	}

}
