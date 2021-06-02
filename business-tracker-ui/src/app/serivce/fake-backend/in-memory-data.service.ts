import {Injectable} from '@angular/core';
import {InMemoryDbService, RequestInfo, ResponseOptions, STATUS} from 'angular-in-memory-web-api';
import {RoadMap} from './modles/road-map';
import {Task} from './modles/task';
import {Sprint} from './modles/sprint';
import {Kpi} from './modles/kpi';
import {Member} from './modles/member';
import {Delivery} from './modles/delivery';
import {Resource} from './modles/resource';
import {Observable} from 'rxjs';
import {ResourceToAdd} from '../../models/resource/resource-to-add';

@Injectable({
  providedIn: 'root'
})
export class InMemoryDataService implements InMemoryDbService {
  const;
  data = {
    roadMaps: [
      {
        id: 1,
        name: 'Product Roadmap'
      }
    ],
    members: [
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
    tasks: [
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
    kpis: [
      {
        name: 'Исходные коды найдены и протестированы',
        taskId: 1,
        id: 1
      },
      {
        name: 'готовые customer journeys',
        taskId: 2,
        id: 2
      },
      {
        name: 'платежная система проверена',
        taskId: 2,
        id: 3
      },
      {
        name: '100 подписок, 10 оплат',
        taskId: 3,
        id: 4
      }
    ],
    sprints: [
      {
        name: '3 сценария для прототипа',
        taskId: 1,
        id: 1,
        finished: true
      },
      {
        name: 'Найти экспертов ИИ',
        taskId: 1,
        id: 2,
        finished: true
      },
      {
        name: 'Поиск открытого кода GitHub',
        taskId: 1,
        id: 3,
        finished: true
      },
      {
        name: 'Прототипирование сценария 1',
        taskId: 2,
        id: 4,
        active: true
      },
      {
        name: 'Прототипирование сценария 2',
        taskId: 2,
        id: 5,
        active: true
      },
      {
        name: 'Прототипирование сценария 3',
        taskId: 2,
        id: 6,
        active: true
      },
      {
        name: 'Прототипирование сценария 4',
        taskId: 2,
        id: 7,
        active: true
      },
      {
        name: 'Подключение платежной системы',
        taskId: 2,
        id: 8
      },
      {
        name: 'Подготовка waitsLists',
        taskId: 3,
        id: 9
      },
      {
        name: 'Разработка лендинга',
        taskId: 3,
        id: 10
      },
      {
        name: 'Запуск компании на ProductHunt',
        taskId: 3,
        id: 11
      },
      {
        name: 'Prototyping init sprint 2',
        taskId: 3,
        id: 14
      }
    ],
    resources: [
      {
        name: 'Михаил Катс',
        time: 48,
        cost: 100,
        sprintId: 1,
        id: 1
      },
      {
        name: 'Артем Хромов',
        time: 25,
        cost: null,
        sprintId: 1,
        id: 2
      },
      {
        name: 'Разработчик',
        time: 33,
        cost: 150,
        sprintId: 2,
        id: 3
      },
      {
        name: 'Верстальщик',
        time: 8,
        cost: 150,
        sprintId: 10,
        id: 4
      },
      {
        name: 'Дизайнер ',
        time: 15,
        cost: 130,
        sprintId: 11,
        id: 5
      }
    ],
    responsibleMembers: [
      {
        name: 'Эдуард',
        lastName: 'Капустин',
        img: 'https://cdn.fakercloud.com/avatars/SlaapMe_128.jpg',
        position: 'Генеральный интеграционный руководитель',
        role: 'founder',
        roadMapId: 1,
        sprintId: 1,
        id: 1
      },
      {
        name: 'Владимир',
        lastName: 'Наумов',
        img: 'https://cdn.fakercloud.com/avatars/airskylar_128.jpg',
        position: 'Глобальный коммуникационный техник',
        role: 'founder',
        roadMapId: 1,
        sprintId: 2,
        id: 2
      },
      {
        name: 'Эдуард',
        lastName: 'Капустин',
        img: 'https://cdn.fakercloud.com/avatars/SlaapMe_128.jpg',
        position: 'Генеральный интеграционный руководитель',
        role: 'founder',
        roadMapId: 1,
        sprintId: 3,
        id: 3
      },
      {
        name: 'Владимир',
        lastName: 'Наумов',
        img: 'https://cdn.fakercloud.com/avatars/airskylar_128.jpg',
        position: 'Глобальный коммуникационный техник',
        role: 'founder',
        roadMapId: 1,
        sprintId: 4,
        id: 4
      },
      {
        name: 'Павел',
        lastName: 'Марков',
        img: 'https://cdn.fakercloud.com/avatars/rob_thomas10_128.jpg',
        position: 'Генеральный маркетинговый руководитель',
        role: 'founder',
        roadMapId: 1,
        sprintId: 5,
        id: 5
      },
      {
        name: 'Владимир',
        lastName: 'Наумов',
        img: 'https://cdn.fakercloud.com/avatars/airskylar_128.jpg',
        position: 'Глобальный коммуникационный техник',
        role: 'founder',
        roadMapId: 1,
        sprintId: 6,
        id: 6
      },
      {
        name: 'Владимир',
        lastName: 'Наумов',
        img: 'https://cdn.fakercloud.com/avatars/airskylar_128.jpg',
        position: 'Глобальный коммуникационный техник',
        role: 'founder',
        roadMapId: 1,
        sprintId: 7,
        id: 7
      },
      {
        name: 'Павел',
        lastName: 'Марков',
        img: 'https://cdn.fakercloud.com/avatars/rob_thomas10_128.jpg',
        position: 'Генеральный маркетинговый руководитель',
        role: 'founder',
        roadMapId: 1,
        sprintId: 8,
        id: 8
      },
      {
        name: 'Павел',
        lastName: 'Марков',
        img: 'https://cdn.fakercloud.com/avatars/rob_thomas10_128.jpg',
        position: 'Генеральный маркетинговый руководитель',
        role: 'founder',
        roadMapId: 1,
        sprintId: 9,
        id: 9
      },
      {
        name: 'Эдуард',
        lastName: 'Капустин',
        img: 'https://cdn.fakercloud.com/avatars/SlaapMe_128.jpg',
        position: 'Генеральный интеграционный руководитель',
        role: 'founder',
        roadMapId: 1,
        sprintId: 10,
        id: 10
      },
      {
        name: 'Владимир',
        lastName: 'Наумов',
        img: 'https://cdn.fakercloud.com/avatars/airskylar_128.jpg',
        position: 'Глобальный коммуникационный техник',
        role: 'founder',
        roadMapId: 1,
        sprintId: 11,
        id: 11
      },
      {
        name: 'Павел',
        lastName: 'Марков',
        img: 'https://cdn.fakercloud.com/avatars/rob_thomas10_128.jpg',
        position: 'Генеральный маркетинговый руководитель',
        role: 'founder',
        roadMapId: 1,
        sprintId: 12,
        id: 12
      },
      {
        name: 'Эдуард',
        lastName: 'Капустин',
        img: 'https://cdn.fakercloud.com/avatars/SlaapMe_128.jpg',
        position: 'Генеральный интеграционный руководитель',
        role: 'founder',
        roadMapId: 1,
        sprintId: 13,
        id: 13
      },
      {
        name: 'Владимир',
        lastName: 'Наумов',
        img: 'https://cdn.fakercloud.com/avatars/airskylar_128.jpg',
        position: 'Глобальный коммуникационный техник',
        role: 'founder',
        roadMapId: 1,
        sprintId: 14,
        id: 14
      }
    ],
    deliveries: [
      {
        name: 'Презентация',
        sprintId: 1,
        id: 1
      },
      {
        name: 'Презентация',
        sprintId: 2,
        id: 2
      },
      {
        name: 'Документ',
        sprintId: 3,
        id: 3
      },
      {
        name: 'Презентация',
        sprintId: 4,
        id: 4
      },
      {
        name: 'Презентация',
        sprintId: 5,
        id: 5
      },
      {
        name: 'Документ',
        sprintId: 6,
        id: 6
      },
      {
        name: 'Презентация',
        sprintId: 7,
        id: 7
      },
      {
        name: 'Презентация',
        sprintId: 8,
        id: 8
      },
      {
        name: 'Документ',
        sprintId: 9,
        id: 9
      }, {
        name: 'Презентация',
        sprintId: 10,
        id: 10
      },
      {
        name: 'Презентация',
        sprintId: 11,
        id: 11
      },
      {
        name: 'Документ',
        sprintId: 12,
        id: 12
      },
      {
        name: 'Презентация',
        sprintId: 13,
        id: 13
      },
      {
        name: 'Презентация',
        sprintId: 14,
        id: 14
      }
    ]
  };

  createDb(reqInfo?: RequestInfo): {} | Observable<{}> | Promise<{}> {
    return this.data;
  }

  genId<T extends RoadMap | Member | Task | Kpi | Sprint | Delivery | Resource>(myTable: T[]): number {
    return myTable.length > 0 ? Math.max(...myTable.map(t => t.id)) + 1 : 11;
  }

  get(reqInfo: RequestInfo): any {
  }

  post(requestInfo: RequestInfo): any {
    const collectionName = requestInfo.collectionName;
    if (collectionName === 'sprints') {

      const data = requestInfo.utils.getJsonBody(requestInfo.req);
      const db = requestInfo.utils.getDb();

      const sprintId = db[`sprints`].map((item: Sprint) => item.id).reduce((cur, next) => cur > next ? cur : next) + 1;
      data.sprint.id = sprintId;
      data[`delivery`].id = db[`deliveries`].map((item: Delivery) => item.id).reduce((cur, next) => cur > next ? cur : next) + 1;
      data[`delivery`].sprintId = sprintId;
      data[`member`].id = db[`responsibleMembers`].map((item: Member) => item.id).reduce((cur, next) => cur > next ? cur : next) + 1;
      data[`member`].sprintId = sprintId;
      data[`resources`]
        .forEach(
          (value: ResourceToAdd) => {
            value.sprintId = sprintId;
            value.id = db[`resources`].map((item: Resource) => item.id).reduce((cur, next) => cur > next ? cur : next) + 1
          });

      this.data.sprints.push(data.sprint);
      this.data.deliveries.push(data.delivery);
      this.data.responsibleMembers.push(data.member);
      this.data.resources.push(...data.resources);

      // forge the response
      const options: ResponseOptions = {
        body: {data},
        status: STATUS.OK,
        headers: requestInfo.headers,
        url: requestInfo.url
      };

      // use createResponse$ to return proper response
      return requestInfo.utils.createResponse$(() => options);
    } else {
      // let the default POST handle all other collections by returning undefined
      return undefined;
    }
  }

  delete(reqInfo: RequestInfo): any {
    const collectionName = reqInfo.collectionName;
    if (collectionName === 'sprints') {
      const db = reqInfo.utils.getDb();
      const sprintId = +reqInfo.url.split('/').pop();
      this.data.deliveries = this.data.deliveries.filter((value: Delivery) => value.sprintId !== sprintId);
      this.data.responsibleMembers = this.data.responsibleMembers.filter(item => item.sprintId !== sprintId);
      this.data.resources = this.data.resources.filter((item: Resource) => item.sprintId !== sprintId);
    } else {
      return undefined;
    }
  }

  put(requestInfo: RequestInfo): any {
  }
}
