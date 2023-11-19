<template>
  <div class="user-list">
    <el-form :inline="true" ref="formSearch" :model="userSearch" :rules="rulesSearch" label-width="80px">
      <el-form-item label="用户id" prop="id">
        <el-input v-model="userSearch.id"></el-input>
      </el-form-item>
      <el-form-item label="用户名" prop="username">
        <el-input v-model="userSearch.username"></el-input>
      </el-form-item>
      <el-form-item label="用户密码" prop="password">
        <el-input v-model="userSearch.password"></el-input>
      </el-form-item>
      <el-form-item label="姓名" prop="name">
        <el-input v-model="userSearch.name"></el-input>
      </el-form-item>
      <el-form-item label="性别" prop="gender">
        <el-radio-group v-model="userSearch.gender">
          <el-radio label="男">男</el-radio>
          <el-radio label="女">女</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="手机号" prop="phone">
        <el-input v-model="userSearch.phone"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="submitFormSearch">提交</el-button>
        <el-button @click="resetFormSearch">重置</el-button>
      </el-form-item>
    </el-form>
    <el-button type="primary" @click="dialogVisibleAdd = true">新增</el-button>
    <el-dialog title="添加用户List" :visible.sync="dialogVisibleAdd" width="50%">
      <AddUser v-on:changeDialogVisibleAdd = "changeDialogVisibleAdd"></AddUser>
      <!-- v-on: 等价于@ -->
    </el-dialog>

    <el-table :data="users" style="width: 100%">
      <el-table-column prop="id" label="用户id" width="150"></el-table-column>
      <el-table-column prop="username" label="用户名"></el-table-column>
      <el-table-column prop="name" label="姓名"></el-table-column>
      <el-table-column prop="gender" label="性别" width="150"></el-table-column>
      <el-table-column prop="phone" label="手机号"></el-table-column>
      <el-table-column label="用户操作" width="300">
        <template slot-scope="scope">
          <div class="button-container">
            <el-button @click="deleteUserIfNeeded(scope)">删除</el-button>
            <!-- </template>
            <template slot-scope="oneRowUserInfo2"> -->
            <el-button type="primary" @click="changeDialog(scope)">修改</el-button>
            <el-button type="success" @click="assignRole(scope)">分配角色</el-button>
          </div>
          <el-dialog title="修改用户信息" :visible.sync="dialogVisibleModify" width="50%">
            <!-- <ModifyUser v-on:changeDialogVisibleModify = "changeDialogVisibleModify" :oneRowUserInfoParent="scope"></ModifyUser> -->
            <h1>修改用户Add</h1><!--修改用户表单-->
            <el-form ref="formModify" :model="userDialog" :rules="rules" label-width="100px">
              <el-form-item label="用户id" prop="id">
                <el-input v-model="userDialog.id"></el-input>
              </el-form-item>
              <el-form-item label="用户名" prop="username">
                <el-input v-model="userDialog.username"></el-input>
              </el-form-item>
              <el-form-item label="用户密码" prop="password">
                <el-input v-model="userDialog.password"></el-input>
              </el-form-item>
              <el-form-item label="姓名" prop="name">
                <el-input v-model="userDialog.name"></el-input>
              </el-form-item>
              <el-form-item label="性别" prop="gender">
                <el-radio-group v-model="userDialog.gender">
                  <el-radio label="男">男</el-radio>
                  <el-radio label="女">女</el-radio>
                </el-radio-group>
              </el-form-item>
              <el-form-item label="手机号" prop="phone">
                <el-input v-model="userDialog.phone"></el-input>
              </el-form-item>
              <el-form-item>
                <el-button type="primary" @click="submitFormModify">提交</el-button>
                <el-button @click="resetFormModify">重置</el-button>
              </el-form-item>
            </el-form>
          </el-dialog>
        </template>
      </el-table-column>
    </el-table>
    <el-dialog title="分配角色" :visible.sync="dialogVisibleRole" width="50%">
      <el-checkbox-group v-model="selectedRoles">
        <el-checkbox v-for="role in roles" :label="role.character_id" :key="role.character_id">
          {{ role.character_name }}
        </el-checkbox>
      </el-checkbox-group>
      <div>已分配的角色: {{ assignedRoles.map(role => role.character_name).join(', ') }}</div>
      <el-button type="primary" @click="saveRoles">保存</el-button>
    </el-dialog>
  </div>
</template>

<script>
import { getInfo } from "@/api/login";
import AddUser from "./Add.vue";
import { deleteUser } from "@/api/login";
import { modifyUser } from '@/api/login.js';
import { searchUser } from '@/api/login.js';
import { getRoles, getAssignedRoles, assignRoles } from '@/api/roles';


export default {
  name: "UserList",
  data() {
    return {
      dialogVisibleAdd: false,
      dialogVisibleModify: false,
      users: [
        {username: "user1",name: "张三",gender: "男",phone: "13812345678",},
        {username: "user2",name: "李四",gender: "女",phone: "13987654321",},
        // more users...
      ],
      userDialog:{
        id:'', username:'', password:'', name:'', gender:'', phone:'', 
      },
      userSearch:{
        id:'', username:'', password:'', name:'', gender:'', phone:'', 
      },
      rules: {
          id: [{ required: true, message: "请输入用户id"}],
          username: [{ required: true, message: "请输入用户名", trigger: "blur" }],
          password: [{ required: true, message: "请输入用户密码", trigger: "blur" }],
          name: [{ required: true, message: "请输入姓名", trigger: "blur" }],
          gender: [{ required: true, message: "请选择性别", trigger: "change" }],
          phone: [{ required: true, message: "请输入手机号", trigger: "blur" }],
      },
      rulesSearch:{
        id: [{ required: false, message: "请输入用户id"}],
        username: [{ required: false, message: "请输入用户名", trigger: "blur" }],
        password: [{ required: false, message: "请输入用户密码", trigger: "blur" }],
        name: [{ required: false, message: "请输入姓名", trigger: "blur" }],
        gender: [{ required: false, message: "请选择性别", trigger: "change" }],
        phone: [{ required: false, message: "请输入手机号", trigger: "blur" }],
      },
      //分配角色的
      dialogVisibleRole: false,
      roles: [],
      assignedRoles: [],
      selectedRoles: [],
      currentUser:{},
    };
  },
  mounted() {
    this.getInfoIfNeeded();
  },
  components:{
    AddUser,
  },
  methods: {
    //获取用户信息
    async getInfoIfNeeded() {
      try {
        const res = await getInfo();
        console.log("res:------------------------",res);
        this.users = res.data.map((item) => ({
          id: item.id,
          username: item.username,
          name: item.name,
          gender: item.gender,
          phone: item.phone,
        }));
      } catch (error) {
        console.error("获取用户信息失败", error);
      }
    },
    async deleteUserIfNeeded(oneRowUserInfo) {
      if (confirm('确定要删除该用户吗？')) {
        try {
          const res = await deleteUser(oneRowUserInfo.row.id);
          console.log("oneRowUserInfo:",oneRowUserInfo,);
          if (res.code === 200) {
            this.$message.success('删除成功');
            this.getInfoIfNeeded();
          } else {
            this.$message.error('删除失败');
          }
        } catch (error) {
          this.$message.error('发生了错误');
          console.error("删除用户失败", error);
        }
      }
    },
    async submitFormModify() {
      try {
        await this.$refs.formModify.validate();
        const res = await modifyUser(this.userDialog);
        if (res.code === 200) {
          this.$message.success('修改成功');
          this.dialogVisibleModify = false;
          this.getInfoIfNeeded();
        } else {
          this.$message.error('修改失败');
        }
      } catch (error) {
        this.$message.error('发生了错误');
        console.error("修改用户失败", error);
      }
    },
    async submitFormSearch() {
      try {
        const res = await searchUser(this.userSearch);
        const users = res.data.map((item) => ({
          id: item.id,
          username: item.username,
          name: item.name,
          gender: item.gender,
          phone: item.phone,
        }));
        this.users = users;
      } catch (error) {
        this.$message.error('搜索失败了');
        console.error("搜索用户失败", error);
      }
    },
    changeDialogVisibleAdd(data){
        // 接收到子组件传递的数据  
        this.dialogVisibleAdd = data;
        this.getInfoIfNeeded();
    },
    changeDialog(scope){
        this.dialogVisibleModify = true;
        this.userDialog = {
          id: scope.row.id,
          username: scope.row.username,
          password: "",
          name: scope.row.name,
          gender: scope.row.gender,
          phone: scope.row.phone,
        };
        this.getInfoIfNeeded();
    },
    resetFormModify() {
        this.$refs.formModify.resetFields();
      },
    resetFormSearch(){
      this.$refs.formSearch.resetFields();
    },
    //分配角色
    async assignRole(scope) {
      try {
        // 获取角色列表
        const ress = await getRoles();
        this.roles = ress.data;
        // 获取当前用户已分配的角色
        const res= await getAssignedRoles({ userId: scope.row.id });
         
        // console.log("getAssignedRoles:",res);

        //  this.assignedRoles = res.data;
        // this.assignedRoles = res.data.map((item) => item.character_id);
        this.assignedRoles = res.data || [];
        console.log("this.assignedRoles:",this.assignedRoles);

        this.selectedRoles=this.assignedRoles.map((item) => item.character_id);

        // 显示分配角色的对话框
        this.dialogVisibleRole = true;
        this.currentUser=scope.row;
      } catch (error) {
        console.error('获取角色信息失败', error);
      }
    },
    async saveRoles() {
      try {
        // 提交选定的角色
        // const plainArray = this.selectedRoles.slice();
        console.log("this.selectedRoles:",this.selectedRoles);
        // console.log("plainArray:",plainArray);

        await assignRoles(this.selectedRoles, this.currentUser.id);

        this.$message.success('角色分配成功');
        this.dialogVisibleRole = false;
      } catch (error) {
        this.$message.error('角色分配失败');
        console.error('角色分配失败', error);
      }
    },
  },
}
</script>
  
<style>
.user-list {
  padding: 20px;
}
.button-container {
  display: flex;
  justify-content: space-between;  /* 这会在容器内平均分配空间 */
}
</style>
  