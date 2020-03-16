/**
 * Copyright (c) 2016-2020, Michael Yang 杨福海 (fuhai999@gmail.com).
 * <p>
 * Licensed under the GNU Lesser General Public License (LGPL) ,Version 3.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.jpress.model;

import io.jboot.db.annotation.Table;
import io.jpress.commons.layer.SortModel;
import io.jpress.model.base.BaseWechatMenu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Generated by Jboot.
 */
@Table(tableName = "wechat_menu", primaryKey = "id")
public class WechatMenu extends BaseWechatMenu<WechatMenu> implements SortModel {

    private static final Map<String,String> typeStrs = new HashMap<>();
    static {
        typeStrs.put("click","触发关键字");
        typeStrs.put("view","链接到网页");
        typeStrs.put("miniprogram","跳转微信小程序");
        typeStrs.put("scancode_push","扫码推事件");
        typeStrs.put("scancode_waitmsg","扫码推事件且弹出“消息接收中”提示框");
        typeStrs.put("pic_sysphoto","系统拍照发图");
        typeStrs.put("pic_photo_or_album","拍照或者相册发图");
        typeStrs.put("pic_weixin","微信相册发图");
        typeStrs.put("location_select","地理位置选择");
    }

    private int layerNumber;
    private SortModel parent;
    private List<SortModel> childs;

    @Override
    public boolean isTop() {
        return getPid() != null && getPid() == 0;
    }

    @Override
    public Long getParentId() {
        return getPid();
    }

    @Override
    public void setParent(SortModel parent) {
        this.parent = parent;
    }

    @Override
    public SortModel getParent() {
        return parent;
    }

    @Override
    public void setChilds(List childs) {
        this.childs = childs;
    }

    @Override
    public void addChild(SortModel child) {
        if (childs == null) {
            childs = new ArrayList<>();
        }
        childs.add(child);
    }

    @Override
    public List getChilds() {
        return childs;
    }

    @Override
    public void setLayerNumber(int layerNumber) {
        this.layerNumber = layerNumber;
    }

    @Override
    public int getLayerNumber() {
        return layerNumber;
    }


    public boolean hasChild() {
        return childs != null && !childs.isEmpty();
    }

    public String getLayerString() {
        if (layerNumber == 0) {
            return "";
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < layerNumber; i++) {
            if (i == 0) {
                sb.append("|—");
            } else {
                sb.append("—");
            }
        }
        return sb.toString();
    }

    public boolean isMyChild(long id) {
        if (childs == null || childs.isEmpty()) {
            return false;
        }

        return isMyChild(childs, id);
    }

    private boolean isMyChild(List<SortModel> categories, long id) {
        for (SortModel category : categories) {
            if (category.getId() == id) {
                return true;
            }

            if (category.getChilds() != null) {
                boolean isChild = isMyChild(category.getChilds(), id);
                if (isChild) return true;
            }
        }
        return false;
    }

    public String getTypeStr(){
        if (hasChild()){
            return "一级菜单";
        }
        String str = typeStrs.get(getType());
        return str != null ? str : "未知类型";
    }



}
