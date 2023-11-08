<template>
  <div class="user-list">
    <el-form :inline="true" ref="formSearch" :model="userSearch" :rules="rulesSearch" label-width="">
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
      <el-table-column prop="id" label="用户id"></el-table-column>
      <el-table-column prop="username" label="用户名"></el-table-column>
      <el-table-column prop="name" label="姓名"></el-table-column>
      <el-table-column prop="gender" label="性别"></el-table-column>
      <el-table-column prop="phone" label="手机号"></el-table-column>
      <el-table-column label="用户操作">
        <template slot-scope="scope">
          <el-button @click="deleteUserIfNeeded(scope)">删除</el-button>
        <!-- </template>
        <template slot-scope="oneRowUserInfo2"> -->
          <el-button type="primary" @click="changeDialog(scope)">修改</el-button>
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
  </div>
</template>

<script>
import { getInfo } from "@/api/login";
import AddUser from "./Add.vue";
import { deleteUser } from "@/api/login";
import { modifyUser } from '@/api/login.js';
import { searchUser } from '@/api/login.js';


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
      }
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
    getInfoIfNeeded(){
      getInfo().then((res) => {
        const users = res.data.map((item) => ({  
          id: item.id, // 根据实际情况调整属性映射  
          username: item.username,  
          name: item.name,  
          gender: item.gender,  
          phone: item.phone,  
        }));  
        this.users = users;
      });
    },
    changeDialogVisibleAdd(data){
        // 接收到子组件传递的数据  
        this.dialogVisibleAdd = data;
        this.getInfoIfNeeded();
    },
    changeDialog(scope){
        this.dialogVisibleModify = true;
        this.userDialog.id=scope.row.id;
        this.userDialog.username=scope.row.username;
        this.userDialog.password="";
        this.userDialog.name=scope.row.name;
        this.userDialog.gender=scope.row.gender;
        this.userDialog.phone=scope.row.phone;
        this.getInfoIfNeeded();
    },
    //删除用户
    deleteUserIfNeeded(oneRowUserInfo) {
      if (confirm('确定要删除该用户吗？')) {
        console.log(oneRowUserInfo);
        console.log(oneRowUserInfo.row.username);
        deleteUser(oneRowUserInfo.row.username)
        .then(res =>{
          if(res.code===200)
            // this.$router.push({ path: '/' })
          {
            this.$message.success('删除成功')
            this.getInfoIfNeeded();
          }
          else
          {
            this.$message.error('删除失败')
          }
        })
        .catch(() => {
          this.$message.error('发生了错误')
          this.loading = false
        })
        // console.log(row);
      };
      this.getInfoIfNeeded();
    },
    submitFormModify() {//提交用户表单
      this.$refs.form.validate(valid => {
        if (valid) {
          modifyUser(this.userDialog)
          .then(res =>{
            if(res.code===200)
            {//修改成功
              this.$message.success('修改成功')
              this.dialogVisibleModify=false;
              this.getInfoIfNeeded();
              // this.resetForm
              // this.$emit('changeDialogVisibleModify',false)
            }
            else
            { // 修改失败
              this.$message.error('修改失败')
            }
          })
          .catch(() => {
            // 修改失败
            this.$message.error('用户名或密码错误')
            this.loading = false
          })
          // TODO: send user data to backend
          console.log(this.userDialog);
        } else {
          console.log("form validation failed");
        }
      });
    },
    submitFormSearch(){
      searchUser(this.userSearch)
      .then(res =>{
        const users = res.data.map((item) => ({  
          id: item.id, // 根据实际情况调整属性映射  
          username: item.username,  
          name: item.name,  
          gender: item.gender,  
          phone: item.phone,  
        }));  
        this.users = users;
        console.log("在then里面");
      })
      .catch(() => {
        // 修改失败
        this.$message.error('搜索失败了');
        this.loading = false;
        // console.log(res);

      })
    },
    resetFormModify() {
        this.$refs.formModify.resetFields();
      },
    resetFormSearch(){
      this.$refs.formSearch.resetFields();
    }
  },
}
</script>
  
<style>
.user-list {
  padding: 20px;
}
</style>
  