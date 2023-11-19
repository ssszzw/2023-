<template>
  <div class="character-list">
    <el-button size="" plain type="primary" @click="openAddDialog('create')">Add</el-button>
    <el-table
    :data="characterTableData.filter(data => filterTableData(data))"
      style="width: 100%"
      :default-sort = "{prop: 'date', order: 'descending'}">
      <el-table-column label="Character_id" prop="character_id" sortable  width="150">
      </el-table-column>
      <el-table-column label="Character_name" prop="character_name" >
      </el-table-column>
      <el-table-column label="Character_type" prop="character_type">
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
              <el-button size="mini" @click="openEditDialog(scope.$index, scope.row)">Edit</el-button>
              <el-button size="mini" type="danger" @click="handleDelete(scope.$index, scope.row)">Delete</el-button>
              <el-button size="mini" link preIcon="ep:basketball" @click="openAssignCharacterDialog(scope.$index, scope.row)">AssignCharacter</el-button>
            </div>
        </template>
      </el-table-column>
    </el-table>
    <editCharacterForm ref="editCharacterFormRef" @success="getCharacterListIfNeeded" />
    <assignCharacterForm ref="assignCharacterFormRef" @success="getCharacterListIfNeeded" />
    <addCharacterForm ref="addCharacterFormRef" @success="getCharacterListIfNeeded" />

  </div>

</template>

<script>
import {getRoles} from '@/api/roles'
import editCharacterForm from './editCharacterForm.vue'
import assignCharacterForm from './assignCharacterForm.vue'
import addCharacterForm from './add_character_form.vue'
import * as CharacterApi from '@/api/roles';

  export default {
    components: {
      editCharacterForm,
      assignCharacterForm,
      addCharacterForm,
    },
    data() {
      return {
        // editCharacterFormRef: null,
        // assignCharacterFormRef: null,

        characterTableData: [{
          character_id: '2016-05-02',
          character_name: '王小虎',
          character_type: '上海市普陀区金沙江路 1518 弄'
        }, {
          character_id: '2016-05-03',
          character_name: '王小虎',
          character_type: '上海市普陀区金沙江路 1516 弄'
        }],
        searchPlaceholder: '',
      }
    },
    mounted() {
      this.getCharacterListIfNeeded();
    },
    methods: {
      filterTableData(data) {//过滤---搜索框内容
        const searchText = this.searchPlaceholder.toLowerCase();
        return (
          !searchText ||
          data.character_id.toString().toLowerCase().includes(searchText) ||
          data.character_name.toLowerCase().includes(searchText)
        );
      },
      async getCharacterListIfNeeded() {//从后端获取角色列表
        try {
          const res = await getRoles(); // Assuming getCharacterList is an asynchronous function that fetches character information
          this.characterTableData = res.data.map((item) => ({
            character_id: item.character_id,
            character_name: item.character_name,
            character_type:item.character_type,
            status:item.status,
            create_time:item.create_time,
            update_time:item.update_time,
            // Include other properties as needed
          }));
        } catch (error) {
          console.error('Failed to fetch character list', error);
        }
      },
      openAddDialog: async function(type, row=0) {//对该角色进行添加
        this.$refs.addCharacterFormRef.open(type,row);
      },

      openEditDialog: async function(index, row) {//对该角色进行编辑
        this.$refs.editCharacterFormRef.open(row);
      },
      // handleDelete(index, row) {//对该角色进行删除
      //   console.log(index, row);
      // },
      handleDelete: async function(index, row) {
        console.log("handleDelete---")
        if(confirm('确定删除角色吗？')){
          try {
            const res=await CharacterApi.deleteCharacter(row.character_id)
            if(res.code===200){
              this.$message.success('删除成功')
              // 刷新列表
              this.getCharacterListIfNeeded();
            } else {
              this.$message.error('删除失败')
            }
          } catch (error){
            this.$message.error('catch中的删除角色失败')
          }
        }
      },
      openAssignCharacterDialog: async function(index, row) {//对该角色进行分配
        this.$refs.assignCharacterFormRef.open(row);
      },
    },
  }
</script>