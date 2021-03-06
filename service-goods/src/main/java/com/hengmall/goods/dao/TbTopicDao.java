package com.hengmall.goods.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import com.hengmall.goods.model.SResources;
import com.hengmall.goods.model.SResourcesEntity;
import com.hengmall.goods.model.TbTopic;
import com.hengmall.goods.model.TbTopicAppraise;
import com.hengmall.goods.model.TbTopicBean;
import com.hengmall.goods.model.TbTopicImg;
import com.hengmall.goods.model.Users;
import com.hengmall.goods.model.api.ProductListResp;

/**
 * Created by Administrator on 2018/5/24.
 */
@Repository
@Mapper
public interface TbTopicDao {


	// 根据ID查询数据
	@Select("select * from tb_topic where id = #{id} limit 1")
	TbTopic queryById(@Param("id") int id);
	
	// 根据ID查询数据
	@Select("select * from tb_topic where id = #{id} limit 1")
	TbTopicBean queryByIdAll(@Param("id") int id);
	
	// 根据话题ID查询商品列表信息数据
	@Select("select a.id ,b.name ,b.headimg ,b.price from tb_topic_product a left join s_product b on a.product_id = b.id where topic_id = #{id}")
	List<ProductListResp> queryProductById(@Param("id") int id);
	
	/**
	 *  新增话题缩略图
	 * @param id  话题ID	
	 * @param productDetail  商品详情数据
	 */
	@Insert("insert into tb_topic_img(topic_id,resources_id,type)"
			+ "values(#{tbTopicImg.topic_id},#{tbTopicImg.resources_id},#{tbTopicImg.type})")
	void insertTopicImg(@Param("tbTopicImg")  TbTopicImg tbTopicImg);
	
	/**
	 * @Title: 查询话题列表 分类为最新 返回给后台使用
	 * @Description: TODO
	 */
	@Select({ "<script>", "select id, topic_type, thumbnail, article, is_recommend, create_time, publisher_type, praise_num, publisher_id, appraise_num, see_num,title,video from tb_topic",
			"order by create_time desc ", "</script>" })
	List<TbTopicBean> topicList();
	
	/**
	 * @Title: 查询话题列表 分类为最新，返回给前端使用
	 * @Description: TODO
	 */
	@Select({ "<script>", "select id, topic_type, thumbnail, article, is_recommend, create_time, publisher_type, praise_num, publisher_id, appraise_num, see_num,title,video,(select count(*) from tb_topic_praise where user_id = #{userId} and topic_id = a.id) as is_appraise from tb_topic a",
			"order by create_time desc ", "</script>" })
	List<TbTopicBean> topicListNew(@Param("userId") int userId);

	/**
	 * @Title: 获取推荐的话题列表
	 * @Description: TODO
	 */
	@Select({ "<script>", "select id, topic_type, thumbnail, article, is_recommend, create_time, publisher_type, praise_num, publisher_id, appraise_num, see_num,title,video, (select count(*) from tb_topic_praise where user_id = #{userId} and topic_id = a.id) as is_appraise from tb_topic a",
			" where is_recommend = '1' order by create_time desc ", "</script>" })
	List<TbTopicBean> topicRecommendList(@Param("userId") int userId);
	
	/**
	 * @Title: 获取推荐的话题列表分类为1:文章，2:视频
	 * @Description: TODO
	 */
	@Select({ "<script>", "select id, topic_type, thumbnail, article, is_recommend, create_time, publisher_type, praise_num, publisher_id, appraise_num, see_num,title,video,(select count(*) from tb_topic_praise where user_id = #{userId} and topic_id = a.id) as is_appraise from tb_topic a",
			" where topic_type = #{type} order by create_time desc ", "</script>" })
	List<TbTopicBean> topicListType(@Param("type") int type,@Param("userId") int userId);
	
	/**
	 * @Title: 获取推荐的话题列表分类为最热门
	 * @Description: TODO
	 */
	@Select({ "<script>", "select id, topic_type, thumbnail, article, is_recommend, create_time, publisher_type, praise_num, publisher_id, appraise_num, see_num,title,video,(select count(*) from tb_topic_praise where user_id = #{userId} and topic_id = a.id) as is_appraise from tb_topic a",
			"  order by appraise_num desc, praise_num desc ", "</script>" })
	List<TbTopicBean> topicHotList(@Param("userId") int userId);
	
	/**
	 * @Title:   获取话题主的话题列表
	 * @Description: TODO
	 */
	@Select({ "<script>", "select id, topic_type, thumbnail, article, is_recommend, create_time, publisher_type, praise_num, publisher_id, appraise_num, see_num,title,video,(select count(*) from tb_topic_praise where user_id = #{userId} and topic_id = a.id) as is_appraise from tb_topic a",
			" where publisher_id = #{publisher_id} order by create_time desc ", "</script>" })
	List<TbTopicBean> topicListByPublisher(@Param("publisher_id") int publisher_id,@Param("userId") int userId);
	
	/**
	 * @Title:   获取个人中心，我的话题列表
	 * @Description: TODO
	 */
	@Select({ "<script>", "select a.id, a.topic_type, a.thumbnail as thumbnailList, a.article as articleJson, a.is_recommend, a.create_time, a.publisher_type, a.praise_num, a.publisher_id, a.appraise_num, a.see_num,title,b.username,"
			+ "b.file,a.video,(select count(*) from tb_topic_praise where user_id = #{userId} and topic_id = a.id) as is_appraise from tb_topic a LEFT JOIN platform.tb_user b ON a.publisher_id = b.id",
			" where a.publisher_id = #{userId} order by a.create_time desc ", "</script>" })
	List<TbTopic> topicListByPublish(@Param("userId") int userId);
	
	/**
	 * @Title:  获取推荐的话题列表分类为1：卖家，2:买家
	 * @Description: TODO
	 */
	@Select({ "<script>", "select id, topic_type, thumbnail, article, is_recommend, create_time, publisher_type, praise_num, publisher_id, appraise_num, see_num,title,video,(select count(*) from tb_topic_praise where user_id = #{userId} and topic_id = a.id) as is_appraise from tb_topic a",
			" where publisher_type = #{type} order by create_time desc ", "</script>" })
	List<TbTopicBean> topicListPublisher(@Param("type") int type,@Param("userId") int userId);
	
	/**
	 *  新增话题
	 * @param id  话题ID	
	 * @param topic  话题数据
	 * 这里在使用json存入数据库时需要注意类型以及更换变量标示符，同时加转义符
	 */
	@Insert("insert into tb_topic(topic_type, thumbnail, article, is_recommend, create_time, publisher_type, praise_num, publisher_id, appraise_num, see_num,title,video)"
			+ "values(#{topic_type},convert("+"\'${thumbnail}\'"+" using utf8mb4), convert("+"\'${article}\'"+" using utf8mb4), #{is_recommend}, "
			+ "now(), #{publisher_type}, #{praise_num}, #{publisher_id}, #{appraise_num}, #{see_num} ,#{title},#{video})")
	@Options(useGeneratedKeys = true, keyProperty = "id")
	int insert(TbTopic topic);
	
	
	// 根据主键id删除数据
	@Delete("delete from tb_topic where id = #{id}")
	int delById(@Param("id") int id);
       
	// 根据主键id修改数据
	@Update("update tb_topic set topic_type =#{edit.topic_type},thumbnail=convert("+"\'${edit.thumbnail}\'"+" using utf8mb4),is_recommend = #{edit.is_recommend},"
			+ "publisher_type =#{edit.publisher_type} ,praise_num=#{edit.praise_num},publisher_id=#{edit.publisher_id},appraise_num=#{edit.appraise_num},see_num=#{edit.see_num},"
			+ "article=convert("+"\'${edit.article}\'"+" using utf8mb4),title =#{edit.title},video =#{edit.video}"
			+ "  where id = #{edit.id}")
	int updateById(@Param("edit") TbTopic topic);
	
	// 根据主键id修改数据
	@Update("update tb_topic set topic_type =#{edit.topic_type},is_recommend = #{edit.is_recommend},"
			+ "publisher_type =#{edit.publisher_type} ,praise_num=#{edit.praise_num},publisher_id=#{edit.publisher_id},appraise_num=#{edit.appraise_num},see_num=#{edit.see_num},"
			+ "title =#{edit.title},video =#{edit.video}"
			+ "  where id = #{edit.id}")
	int updateById2(@Param("edit") TbTopic topic);
	
	/**
	 * 获取话题记录总数
	 * @return
	 */
	@Select("select count(*) from tb_topic")
	int countTopic();
	
	// 根据主键id删除数据
	@Delete("delete from tb_topic_img where topic_id = #{topic_id} and resources_id = #{resources_id}")
	int delTopicImgById(@Param("topic_id") int topic_id,@Param("resources_id") int resources_id);
	
	/**
	 * 获取话题记录总数
	 * @return
	 */
	@Select("select count(*) from tb_topic_praise where user_id = #{userId} and topic_id = #{topicId}")
	int searchTopic(@Param("userId") int userId,@Param("topicId") int topicId);
	
	/**
	 * 获取话题评论点赞记录总数
	 * @return
	 */
	@Select("select count(*) from tb_appraise_praise where user_id = #{userId} and appraise_id = #{appraiseId}")
	int searchAppraise(@Param("userId") int userId,@Param("appraiseId") int appraiseId);
	
	/**
	 *  新增话题点赞记录
	 * @param id  话题ID	
	 * @param productDetail  商品详情数据
	 */
	@Insert("insert into tb_topic_praise(topic_id,user_id)"
			+ "values(#{topicId},#{userId})")
	int insertTopicPraise(@Param("userId") int userId,@Param("topicId") int topicId);
	
	// 根据主键id删除点赞记录
	@Delete("delete from tb_topic_praise where topic_id = #{topicId} and user_id = #{userId}")
	int delTopicPraiseById(@Param("userId") int userId,@Param("topicId") int topicId);
	
	/**
	 * 获取话题记录总数
	 * @return
	 */
	@Select("select id from users where token = #{token}")
	Users searchUsers(@Param("token") String token);

	/**
	 *  新增话题评论记录
	 * @param userId  用户ID		
	 * @param topicId  话题ID
	 * @param appraiseUserId  被评论人ID
	 * @param context  评论内容
	 * @return
	 */
	@Insert("insert into tb_topic_appraise(topic_id,user_id,create_time,appraise_user,context)"
			+ "values(#{topic_id},#{user_id},now(),#{appraise_user},#{context})")
	@Options(useGeneratedKeys = true, keyProperty = "id")
	int insertTopicAppraise(TbTopicAppraise appraise);
	
	/**
	 * 获取话题评论信息
	 * @return
	 */
	@Select("select a.id,a.user_id,a.create_time,a.look,a.appraise_user,a.topic_id,a.context, b.nickname as userName ,b.avatar_url as url from tb_topic_appraise a left join users b on a.user_id = b.id where a.id = #{id} order by create_time asc ")
	TbTopicAppraise queryAppraise(@Param("id") int id);
	
	// 根据主键id修改数据
	@Update("update tb_topic_appraise set look =#{edit.look}"
			+ " where id = #{edit.id}")
	int updateAppraiseById(@Param("edit") TbTopicAppraise appraise);
	
	/**
	 * 查询搜索内容相关数据
	 * @return
	 */
	@Select("select * from tb_topic where `article` like '%${context}%' ")
	List<TbTopicBean> searchTopicList(@Param("context") String context);
	
	/**
	 * 根据话题ID查询所有评论列表
	 * @param topicId
	 * @return
	 */
	@Select("select a.id,a.user_id,a.create_time,a.look,a.appraise_user,a.topic_id,a.context, b.nickname as userName ,b.avatar_url as url,(select count(*) from tb_appraise_praise where user_id = #{userId} and appraise_id = a.id) as is_appraise from tb_topic_appraise a left join users b on a.user_id = b.id where a.topic_id = #{topicId} order by a.create_time asc")
	List<TbTopicAppraise> queryTopicAppraise(@Param("topicId") int topicId,@Param("userId") int userId);
	
	
	/**
	 * 获取话题评论点赞记录总数
	 * @return
	 */
	@Select("select count(*) from tb_appraise_praise where user_id = #{userId} and appraise_id = #{appraiseId}")
	int searchAppraiseTopic(@Param("userId") int userId,@Param("appraiseId") int appraiseId);
	
	/**
	 * 获取话题评论点赞总数
	 * @return
	 */
	@Select("select count(*) from tb_appraise_praise where appraise_id = #{appraiseId}")
	int countAppraisePraise(@Param("appraiseId") int appraiseId);
	
	/**
	 *  新增话题评论点赞记录
	 * @param id  话题ID	
	 * @param productDetail  商品详情数据
	 */
	@Insert("insert into tb_appraise_praise(appraise_id,user_id)"
			+ "values(#{appraiseId},#{userId})")
	int insertAppraisePraise(@Param("userId") int userId,@Param("appraiseId") int appraiseId);
	
	// 根据主键id删除话题评论点赞记录
	@Delete("delete from tb_appraise_praise where appraise_id = #{appraiseId} and user_id = #{userId}")
	int delAppraisePraiseById(@Param("userId") int userId,@Param("appraiseId") int appraiseId);
	
	/**
	 * 获取话题点赞总数
	 * @return
	 */
	@Select("select count(*) from tb_topic_praise where topic_id = #{topicId}")
	int countTopicPraise(@Param("topicId") int topicId);

	
	/**
	 * 根据评论ID查询所有评论子列表
	 * @param appraiseId
	 * @return
	 */
	@Select("select a.id,a.user_id,a.create_time,a.look,a.appraise_user,a.topic_id,a.context, b.nickname as userName ,b.avatar_url as url,(select count(*) from tb_appraise_praise where user_id = #{userId} and appraise_id = a.id) as is_appraise from tb_topic_appraise a left join users b on a.user_id = b.id where a.appraise_user = #{appraiseId} OR a.id= #{appraiseId} order by a.create_time asc")
	List<TbTopicAppraise> queryTopicAppraiseChild(@Param("appraiseId") int appraiseId,@Param("userId") int userId);

	@Select("select * from tb_topic_appraise where id = #{id}")
	TbTopicAppraise queryAppraisePraise(@Param("id") int id);
	
	// 根据商品ID查询（图片、视频）资源地址 用于返回给前端使用的特定格式
	@Select("select DISTINCT a.id as id,a.id as `name`, a.path as url from s_resources a,tb_topic_img b where "
			+ "b.topic_id = #{id} and b.type = #{type} and a.id = b.resources_id")
	List<SResources> queryByTopicId(@Param("id") int id,@Param("type") int type);
	
	// 根据商品ID查询（图片、视频）资源地址 用于返回给前端使用的特定格式
	@Select("select DISTINCT a.id as id,a.id as `name`, a.path as url from s_resources a,tb_topic_img b where "
			+ "b.topic_id = #{id} and b.type = #{type} and a.id = b.resources_id")
	List<SResourcesEntity> queryByTopicIdAll(@Param("id") int id,@Param("type") int type);
	
	/** 根据主键id删除商品资源数据 */
	@Update("delete from s_resources where id = #{id}" )
	void deleteResources(@Param("id") int id);
}
