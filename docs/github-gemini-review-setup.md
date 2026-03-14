# GitHub Gemini Code Review 설정 가이드

이 문서는 Gemini를 사용하여 GitHub Pull Request 시 자동으로 코드 리뷰를 수행하도록 설정하는 방법을 설명합니다.

## 1. Gemini API 키 발급
1. [Google AI Studio](https://aistudio.google.com/app/apikey)에 접속합니다.
2. 'Create API key'를 클릭하여 새로운 키를 생성하고 복사해둡니다.

## 2. GitHub Repository Secrets 등록
1. 리뷰를 적용할 GitHub 저장소의 **Settings** 탭으로 이동합니다.
2. 왼쪽 사이드바에서 **Secrets and variables** > **Actions**를 클릭합니다.
3. **Secrets** 탭(기본값)에 있는지 확인합니다. (**Environment secrets**가 아닌 **Repository secrets** 섹션을 사용하세요.)
4. **New repository secret** 버튼을 클릭합니다.
5. Name에 `GEMINI_API_KEY`를 입력합니다.
6. Value에 발급받은 API 키를 붙여넣고 **Add secret**을 클릭합니다.

> **참고: GITHUB_TOKEN은 무엇인가요?**
> 워크플로우 파일에 있는 `GITHUB_TOKEN`은 GitHub Actions가 실행될 때 자동으로 생성되는 인증 토큰입니다. 별도로 등록할 필요가 없으며, Gemini가 PR에 댓글을 남길 수 있는 권한을 제공합니다.

## 3. 설정 방식 비교 및 추천 (Environment vs Repository)

GitHub에서 변수를 등록하는 방식은 크게 두 가지가 있으며, 상황에 맞는 선택이 필요합니다.

| 구분 | Repository Secrets (권장) | Environment Secrets/Variables |
| :--- | :--- | :--- |
| **개념** | 저장소 전체에서 공유되는 비밀값 | 특정 환경(local, prod 등)에서만 사용되는 값 |
| **보안** | 값이 마스킹되어 로그에 남지 않음 | Secrets는 마스킹되나, Variables는 노출될 수 있음 |
| **Workflow 설정** | 추가 설정 없이 즉시 참조 가능 | `environment: <이름>` 설정을 명시해야 함 |
| **주 용도** | API 키, 인증 토큰 등 범용 비밀값 | 개발/운영 서버별로 다른 설정값 |

### 💡 추천하는 방식: **Repository Secrets**
- **간결함:** `.yml` 파일에서 별도의 환경 지정 없이 `${{ secrets.GEMINI_API_KEY }}`로 바로 불러올 수 있어 실수를 방지합니다.
- **보안:** `Variables`와 달리 `Secrets`로 등록하면 API 키가 로그에 노출되지 않도록 GitHub가 자동으로 보호합니다.
- **적합성:** Gemini API 키는 보통 환경별로 다르게 쓰기보다는 저장소 전체의 코드 리뷰용으로 하나를 공유하므로 Repository 수준이 가장 적절합니다.

## 4. GitHub Actions 워크플로우 확인
- `.github/workflows/gemini-review.yml` 파일이 PR 생성 및 업데이트 시 자동으로 실행되도록 설정되어 있습니다.
- PR이 생성되거나 새로운 커밋이 푸시되면 Gemini가 코드 차이점(diff)을 분석하여 댓글로 리뷰를 남깁니다.
