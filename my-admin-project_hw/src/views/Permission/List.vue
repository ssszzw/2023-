<template>
    <div class="permission-list">
      <el-button size="" plain type="primary" @click="openAddDialog('create','')">Add</el-button>
      <el-table
      :data="permissionTableData.filter(data => filterTableData(data))"
        style="width: 100%"
        :default-sort = "{prop: 'date', order: 'descending'}">
        <el-table-column label="Permission_id" prop="permission_id" sortable  width="150">
        </el-table-column>
        <el-table-column label="Parent_id" prop="parent_id" sortable  width="150">
        </el-table-column>
        <el-table-column label="Permission_name" prop="permission_name" width="150px">
        </el-table-column>
        <el-table-column label="path" prop="path">
        </el-table-column>
        <el-table-column label="order_num" prop="order_num">
        </el-table-column>
        <el-table-column label="description" prop="description">
        </el-table-column>
        <el-table-column label="status" prop="status">
        </el-table-column>
        <el-table-column label="create_time" prop="create_time">
        </el-table-column>
        <el-table-column label="update_time" prop="update_time">
        </el-table-column>
        <el-table-column align="left" width="300">
          <!-- <template slot="header" slot-scope="scope" > -->
          <template slot="header">
            <el-input v-model="searchPlaceholder" size="mini" placeholder="输入关键字搜索"/>
          </template>
          <template slot-scope="scope">
              <div style="display: flex; align-items: center;">
                <el-button size="mini" @click="openAddDialog('delete', scope.row)">Edit</el-button>
                <el-button size="mini" type="danger" @click="handleDelete(scope.$index, scope.row)">Delete</el-button>
              </div>
          </template>
        </el-table-column>
      </el-table>
      <!-- <editCharacterForm ref="editPermissionFormRef" @success="getPermissionListIfNeeded" /> -->
      <addPermissionForm ref="addPermissionFormRef" @success="getPermissionListIfNeeded" />
  
    </div>
</template>
<script>
import {getAllPermissions} from '@/api/permission'
// import editCharacterForm from './editCharacterForm.vue'
import addPermissionForm from '@/views/Permission/add_permission_form.vue'
import * as PermissionApi from '@/api/permission';

  export default {
    components: {
    //   editCharacterForm,
      addPermissionForm,
    },
    data() {
      return {
        // editCharacterFormRef: null,
        // assignCharacterFormRef: null,

        permissionTableData: [{
            permission_id: '2016-05-02',
            permission_name: '王小虎',
            permission_type: '上海市普陀区金沙江路 1518 弄'
        }, {
            permission_id: '2016-05-03',
            permission_name: '王小虎',
            permission_type: '上海市普陀区金沙江路 1516 弄'
        }],
        searchPlaceholder: '',
      }
    },
    mounted() {
      this.getPermissionListIfNeeded();
    },
    methods: {
      filterTableData(data) {//过滤---搜索框内容
        const searchText = this.searchPlaceholder.toLowerCase();
        return (
          !searchText ||
          data.permission_id.toString().toLowerCase().includes(searchText) ||
          data.permission_name.toLowerCase().includes(searchText) ||
          data.path.toLowerCase().includes(searchText)

        );
      },
      async getPermissionListIfNeeded() {//从后端获取角色列表
        try {
          const res = await getAllPermissions(); // Assuming getCharacterList is an asynchronous function that fetches character information
          this.permissionTableData = res.data.map((item) => ({
            permission_id: item.permission_id,
            parent_id: item.parent_id,
            permission_name: item.permission_name,
            path:item.path,
            order_num:item.order_num,
            description:item.description,
            status:item.status,
            create_time:item.create_time,
            update_time:item.update_time,
            // Include other properties as needed
          }));
        } catch (error) {
          console.error('Failed to fetch permission list', error);
        }
      },
      openAddDialog: async function(type, row) {//对该角色进行添加
        this.$refs.addPermissionFormRef.open(type,row);
      },

    //   openEditDialog: async function(index, row) {//对该角色进行编辑
    //     this.$refs.editCharacterFormRef.open(row);
    //   },
      // handleDelete(index, row) {//对该角色进行删除
      //   console.log(index, row);
      // },
      handleDelete: async function(index, row) {
        console.log("handleDelete---")
        if(confirm('确定删除权限吗？')){
          try {
            const res=await PermissionApi.deletePermission(row.permission_id)
            if(res.code===200){
              this.$message.success('删除成功')
              // 刷新列表
              this.getPermissionListIfNeeded();
            } else {
              this.$message.error('删除失败')
            }
          } catch (error){
            this.$message.error('catch中的删除权限失败')
          }
        }
      },
    //   openAssignCharacterDialog: async function(index, row) {//对该角色进行分配
    //     this.$refs.assignCharacterFormRef.open(row);
    //   },
    },
  }
</script>