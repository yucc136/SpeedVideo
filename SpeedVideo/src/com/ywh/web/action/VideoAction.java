package com.ywh.web.action;

import java.text.DecimalFormat;
import java.util.List;

import com.ywh.biz.CategoryBiz;
import com.ywh.biz.VideoBiz;
import com.ywh.entity.Category;
import com.ywh.entity.Comment;
import com.ywh.entity.User;
import com.ywh.entity.Video;

/**
 * 视频Action，负责显示视频列表，显示播放页面
 * 
 * @author YWH
 */
public class VideoAction extends BaseAction {
	private VideoBiz videoBiz;
	private CategoryBiz categoryBiz;
	private List<Video> videolist;
	private List<Video> videolist2;
	private List<Video> videolist3;
	private List<Comment> comments;
	private Video video;
	private Category subcategory;// 分类
	private Category maincategory;// 上一层分类
	private String serach_text;// 搜索关键字
	private String score;// 评分
	private String totalscore;// 总评分
	private int page = 1;// 页数
	private int pageSize = 4;// 每页数
	private int p1 = 1;// p1页数
	private int p2 = 1;// p2页数
	private int p3 = 1;// p3页数
	private int maxPage;

	/**
	 * 显示首页视频列表
	 */
	public String showVideo() {
		videolist = videoBiz.getIndexNew(p1, pageSize);
		videolist2 = videoBiz.getIndexViews(p2, pageSize);
		videolist3 = videoBiz.getIndexMostComment(p3, pageSize);
		return "showVideo";
	}

	/**
	 * 播放页面
	 */
	public String play() {
		// 设置分数
		User user = (User) session.get("user");
		DecimalFormat dcm = new DecimalFormat("0.00");
		if (user != null
				&& videoBiz.getScore(video.getId(), user.getId()) != null) {
			score = dcm.format((Double) videoBiz.getScore(video.getId(), user
					.getId()));
		} else {
			score = "0";
		}
		if (videoBiz.getTotalScore(video.getId()) != null) {
			totalscore = dcm.format((Double) videoBiz.getTotalScore(video
					.getId()));
		} else {
			totalscore = "0.00";
		}
		video = videoBiz.getVideoPlay(video.getId());
		videoBiz.updateViews(video);
		comments = videoBiz.getComments(video, page, pageSize);
		for (Comment comment : comments) {
			comment.setUser(videoBiz.findCmUser(comment.getUid()));
		}
		maxPage = (videoBiz.findCommentCount() % pageSize == 0 ? videoBiz
				.findCommentCount()
				/ pageSize : videoBiz.findCommentCount() / pageSize+1);
		return "play";
	}

	/**
	 * 根据左边栏的目录显示视频列表
	 */
	public String showByCategory() {
		// 显示目录信息
		subcategory = categoryBiz.getCategoryById(subcategory.getId());
		maincategory = categoryBiz.getCategoryById(maincategory.getId());
		// 视频列表
		videolist = videoBiz.showByCategory(subcategory.getId());
		return "showByCategory";
	}

	/**
	 * 显示我的最爱列表
	 */
	public String favlist() {
		User user = (User) session.get("user");
		List<Integer> idlist = videoBiz.findIdByFavorite(user, p1, pageSize);
		List<Integer> idlist2 = videoBiz
				.findIdByVideolist(user, p2, pageSize);
		List<Integer> idlist3 = videoBiz.findIdByFavorite(user, p3, pageSize);
		videolist = videoBiz.showBylist(idlist);
		videolist2 = videoBiz.showBylist(idlist2);
		videolist3 = videoBiz.showBylist(idlist2);
		return "favlist";
	}

	/**
	 * 点击按钮添加到我的最爱列表
	 */
	public String addtoFav() {
		User user = (User) session.get("user");
		Object valid = videoBiz.validFav(user.getId(), video.getId());
		if (valid == null) {
			videoBiz.addtoFav(video.getId(), user);
		}
		return "addtoFav";
	}

	/**
	 * 搜索功能
	 */
	public String serach() {
		videolist = videoBiz.serach(serach_text);
		return "showByCategory";
	}

	public void setVideoBiz(VideoBiz videoBiz) {
		this.videoBiz = videoBiz;
	}

	public Video getVideo() {
		return video;
	}

	public void setVideo(Video video) {
		this.video = video;
	}

	public List<Video> getVideolist() {
		return videolist;
	}

	public void setVideolist(List<Video> videolist) {
		this.videolist = videolist;
	}

	public Category getSubcategory() {
		return subcategory;
	}

	public void setSubcategory(Category subcategory) {
		this.subcategory = subcategory;
	}

	public void setCategoryBiz(CategoryBiz categoryBiz) {
		this.categoryBiz = categoryBiz;
	}

	public Category getMaincategory() {
		return maincategory;
	}

	public void setMaincategory(Category maincategory) {
		this.maincategory = maincategory;
	}

	public String getSerach_text() {
		return serach_text;
	}

	public void setSerach_text(String serachText) {
		serach_text = serachText;
	}

	public List<Video> getVideolist2() {
		return videolist2;
	}

	public void setVideolist2(List<Video> videolist2) {
		this.videolist2 = videolist2;
	}

	public List<Video> getVideolist3() {
		return videolist3;
	}

	public void setVideolist3(List<Video> videolist3) {
		this.videolist3 = videolist3;
	}

	public VideoBiz getVideoBiz() {
		return videoBiz;
	}

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}

	public String getTotalscore() {
		return totalscore;
	}

	public void setTotalscore(String totalscore) {
		this.totalscore = totalscore;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getMaxPage() {
		return maxPage;
	}

	public void setMaxPage(int maxPage) {
		this.maxPage = maxPage;
	}

	public int getP1() {
		return p1;
	}

	public void setP1(int p1) {
		this.p1 = p1;
	}

	public int getP2() {
		return p2;
	}

	public void setP2(int p2) {
		this.p2 = p2;
	}

	public int getP3() {
		return p3;
	}

	public void setP3(int p3) {
		this.p3 = p3;
	}

}
