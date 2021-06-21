import {Injectable} from '@angular/core';
import {InMemoryDbService, RequestInfo, ResponseOptions, STATUS} from 'angular-in-memory-web-api';
import {RoadMap} from './modles/road-map';
import {MileStone} from './modles/mileStone';
import {Task} from './modles/task';
import {Kpi} from './modles/kpi';
import {Member} from './modles/member';
import {Resource} from './modles/resource';
import {Observable} from 'rxjs';
import {ResourceToAdd} from '../../models/resource/resource-to-add';

@Injectable({
  providedIn: 'root'
})
export class InMemoryDataService implements InMemoryDbService {
  const;
  data = {
    roadmap: [
      {
        id: 1,
        name: 'Product Roadmap'
      }
    ],
    member: [
      {
        id: 1,
        name: 'Константин',
        lastName: 'Крылов',
        img: 'https://cdn.fakercloud.com/avatars/prrstn_128.jpg',
        roadMapId: 1,
        position: 'Региональный интеграционный сотрудник',
        role: 'founder'
      },
      {
        id: 2,
        name: 'Эдуард',
        lastName: 'Капустин',
        img: 'https://cdn.fakercloud.com/avatars/SlaapMe_128.jpg',
        roadMapId: 1,
        position: 'Генеральный интеграционный руководитель',
        role: 'founder'
      },
      {
        id: 3,
        name: 'Владимир',
        lastName: 'Наумов',
        img: 'https://cdn.fakercloud.com/avatars/airskylar_128.jpg',
        roadMapId: 1,
        position: 'Глобальный коммуникационный техник',
        role: 'founder'
      },
      {
        id: 4,
        name: 'Павел',
        lastName: 'Марков',
        img: 'https://cdn.fakercloud.com/avatars/rob_thomas10_128.jpg',
        roadMapId: 1,
        position: 'Генеральный маркетинговый руководитель',
        role: 'founder'
      }
    ],
    milestone: [
      {
        id: 1,
        name: 'Research',
        startDate: '2021-04-19T20:30:27.271Z',
        finishDate: '2021-05-03T20:30:27.271Z',
        roadMapId: 1
      },
      {
        id: 2,
        name: 'Prototyping',
        startDate: '2021-05-03T20:30:27.271Z',
        finishDate: '2021-05-17T20:30:27.271Z',
        roadMapId: 1
      },
      {
        id: 3,
        name: 'Market-fit',
        startDate: '2021-05-17T20:30:27.271Z',
        finishDate: '2021-05-31T20:30:27.271Z',
        roadMapId: 1
      }
    ],
    kpi: [
      {
        name: 'Исходные коды найдены и протестированы',
        mileStoneId: 1,
        id: 1
      },
      {
        name: 'готовые customer journeys',
        mileStoneId: 2,
        id: 2
      },
      {
        name: 'платежная система проверена',
        mileStoneId: 2,
        id: 3
      },
      {
        name: '100 подписок, 10 оплат',
        mileStoneId: 3,
        id: 4
      }
    ],
    task: [
      {
        name: '3 сценария для прототипа',
        mileStoneId: 1,
        id: 1,
        finished: true,
        delivery: "Document"
      },
      {
        name: 'Найти экспертов ИИ',
        mileStoneId: 1,
        id: 2,
        finished: true,
        delivery: "Document"
      },
      {
        name: 'Поиск открытого кода GitHub',
        mileStoneId: 1,
        id: 3,
        finished: true,
        delivery: "Document"
      },
      {
        name: 'Прототипирование сценария 1',
        mileStoneId: 2,
        id: 4,
        active: true,
        delivery: "Document"
      },
      {
        name: 'Прототипирование сценария 2',
        mileStoneId: 2,
        id: 5,
        active: true,
        delivery: "Document"
      },
      {
        name: 'Прототипирование сценария 3',
        mileStoneId: 2,
        id: 6,
        active: true,
        delivery: "Presentation"
      },
      {
        name: 'Прототипирование сценария 4',
        mileStoneId: 2,
        id: 7,
        active: true,
        delivery: "Presentation"
      },
      {
        name: 'Подключение платежной системы',
        mileStoneId: 2,
        id: 8,
        delivery: "Presentation"
      },
      {
        name: 'Подготовка waitsLists',
        mileStoneId: 3,
        id: 9,
        delivery: "Presentation"
      },
      {
        name: 'Разработка лендинга',
        mileStoneId: 3,
        id: 10,
        delivery: "Presentation"
      },
      {
        name: 'Запуск компании на ProductHunt',
        mileStoneId: 3,
        id: 11,
        delivery: "Presentation"
      },
      {
        name: 'Prototyping init sprint 2',
        mileStoneId: 3,
        id: 14,
        delivery: "Presentation"
      }
    ],
    resource: [
      {
        name: 'Михаил Катс',
        time: 48,
        cost: 100,
        taskId: 1,
        id: 1
      },
      {
        name: 'Артем Хромов',
        time: 25,
        cost: null,
        taskId: 1,
        id: 2
      },
      {
        name: 'Разработчик',
        time: 33,
        cost: 150,
        taskId: 2,
        id: 3
      },
      {
        name: 'Верстальщик',
        time: 8,
        cost: 150,
        taskId: 10,
        id: 4
      },
      {
        name: 'Дизайнер ',
        time: 15,
        cost: 130,
        taskId: 11,
        id: 5
      }
    ],
    responsibleMember: [
      {
        name: 'Эдуард',
        lastName: 'Капустин',
        img: 'https://cdn.fakercloud.com/avatars/SlaapMe_128.jpg',
        position: 'Генеральный интеграционный руководитель',
        role: 'founder',
        roadMapId: 1,
        taskId: 1,
        id: 1
      },
      {
        name: 'Владимир',
        lastName: 'Наумов',
        img: 'https://cdn.fakercloud.com/avatars/airskylar_128.jpg',
        position: 'Глобальный коммуникационный техник',
        role: 'founder',
        roadMapId: 1,
        taskId: 2,
        id: 2
      },
      {
        name: 'Эдуард',
        lastName: 'Капустин',
        img: 'https://cdn.fakercloud.com/avatars/SlaapMe_128.jpg',
        position: 'Генеральный интеграционный руководитель',
        role: 'founder',
        roadMapId: 1,
        taskId: 3,
        id: 3
      },
      {
        name: 'Владимир',
        lastName: 'Наумов',
        img: 'https://cdn.fakercloud.com/avatars/airskylar_128.jpg',
        position: 'Глобальный коммуникационный техник',
        role: 'founder',
        roadMapId: 1,
        taskId: 4,
        id: 4
      },
      {
        name: 'Павел',
        lastName: 'Марков',
        img: 'https://cdn.fakercloud.com/avatars/rob_thomas10_128.jpg',
        position: 'Генеральный маркетинговый руководитель',
        role: 'founder',
        roadMapId: 1,
        taskId: 5,
        id: 5
      },
      {
        name: 'Владимир',
        lastName: 'Наумов',
        img: 'https://cdn.fakercloud.com/avatars/airskylar_128.jpg',
        position: 'Глобальный коммуникационный техник',
        role: 'founder',
        roadMapId: 1,
        taskId: 6,
        id: 6
      },
      {
        name: 'Владимир',
        lastName: 'Наумов',
        img: 'https://cdn.fakercloud.com/avatars/airskylar_128.jpg',
        position: 'Глобальный коммуникационный техник',
        role: 'founder',
        roadMapId: 1,
        taskId: 7,
        id: 7
      },
      {
        name: 'Павел',
        lastName: 'Марков',
        img: 'https://cdn.fakercloud.com/avatars/rob_thomas10_128.jpg',
        position: 'Генеральный маркетинговый руководитель',
        role: 'founder',
        roadMapId: 1,
        taskId: 8,
        id: 8
      },
      {
        name: 'Павел',
        lastName: 'Марков',
        img: 'https://cdn.fakercloud.com/avatars/rob_thomas10_128.jpg',
        position: 'Генеральный маркетинговый руководитель',
        role: 'founder',
        roadMapId: 1,
        taskId: 9,
        id: 9
      },
      {
        name: 'Эдуард',
        lastName: 'Капустин',
        img: 'https://cdn.fakercloud.com/avatars/SlaapMe_128.jpg',
        position: 'Генеральный интеграционный руководитель',
        role: 'founder',
        roadMapId: 1,
        taskId: 10,
        id: 10
      },
      {
        name: 'Владимир',
        lastName: 'Наумов',
        img: 'https://cdn.fakercloud.com/avatars/airskylar_128.jpg',
        position: 'Глобальный коммуникационный техник',
        role: 'founder',
        roadMapId: 1,
        taskId: 11,
        id: 11
      },
      {
        name: 'Павел',
        lastName: 'Марков',
        img: 'https://cdn.fakercloud.com/avatars/rob_thomas10_128.jpg',
        position: 'Генеральный маркетинговый руководитель',
        role: 'founder',
        roadMapId: 1,
        taskId: 12,
        id: 12
      },
      {
        name: 'Эдуард',
        lastName: 'Капустин',
        img: 'https://cdn.fakercloud.com/avatars/SlaapMe_128.jpg',
        position: 'Генеральный интеграционный руководитель',
        role: 'founder',
        roadMapId: 1,
        taskId: 13,
        id: 13
      },
      {
        name: 'Владимир',
        lastName: 'Наумов',
        img: 'https://cdn.fakercloud.com/avatars/airskylar_128.jpg',
        position: 'Глобальный коммуникационный техник',
        role: 'founder',
        roadMapId: 1,
        taskId: 14,
        id: 14
      }
    ]
  };

  createDb(reqInfo?: RequestInfo): {} | Observable<{}> | Promise<{}> {
    return this.data;
  }

  genId<T extends RoadMap | Member | MileStone | Kpi | Task | Resource>(myTable: T[]): number {
    return myTable.length > 0 ? Math.max(...myTable.map(t => t.id)) + 1 : 11;
  }

  // get(reqInfo: RequestInfo): any {
  // }

  post(requestInfo: RequestInfo): any {
    const collectionName = requestInfo.collectionName;
    if (collectionName === 'task') {

      const data = requestInfo.utils.getJsonBody(requestInfo.req);
      const db = requestInfo.utils.getDb();

      const taskId = db[`task`].map((item: Task) => item.id).reduce((cur, next) => cur > next ? cur : next) + 1;
      data.task.id = taskId;
      data[`member`].id = db[`responsibleMember`].map((item: Member) => item.id).reduce((cur, next) => cur > next ? cur : next) + 1;
      data[`member`].taskId = taskId;
      data[`resources`]
        .forEach(
          (value: ResourceToAdd) => {
            value.sprintId = taskId;
            value.id = db[`resource`].map((item: Resource) => item.id).reduce((cur, next) => cur > next ? cur : next) + 1
          });

      this.data.task.push(data.task);
      this.data.responsibleMember.push(data.member);
      this.data.resource.push(...data.resources);

      // forge the response
      const options: ResponseOptions = {
        body: {data},
        status: STATUS.OK,
        headers: requestInfo.headers,
        url: requestInfo.url
      };

      return requestInfo.utils.createResponse$(() => options);
    } else {
      return undefined;
    }
  }

  delete(reqInfo: RequestInfo): any {
    const collectionName = reqInfo.collectionName;
    if (collectionName === 'task') {
      const sprintId = +reqInfo.url.split('/').pop();
      this.data.responsibleMember = this.data.responsibleMember.filter(item => item.taskId !== sprintId);
      this.data.resource = this.data.resource.filter((item: Resource) => item.taskId !== sprintId);
    } else {
      return undefined;
    }
  }

  // put(requestInfo: RequestInfo): any {
  // }
}
